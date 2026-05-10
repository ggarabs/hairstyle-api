(ns hairstyle-api.diplomat.http-server 
  (:require
   [clojure.set :as set]
   [io.pedestal.http :as http]
   [io.pedestal.http.body-params :refer [body-params default-parser-map]]
   [io.pedestal.http.route :refer [expand-routes]]
   [hairstyle-api.controllers.hairstyle :as controllers.hairstyle]
   [hairstyle-api.db.config :refer [conn]]
   [hairstyle-api.db.hairstyle :as db.hairstyle]
   [hairstyle-api.wire.in.hairstyle :as wire.in]
   [hairstyle-api.interceptors.http :as interceptors.http]
   [hairstyle-api.interceptors.db :as interceptors.db]
   [hairstyle-api.adapters.hairstyle :as adapters.hairstyle]))

(def common-interceptors
  [http/log-request
   (body-params)
   http/json-body
   (interceptors.db/inject-db (db.hairstyle/->DatomicDB conn))])

(def body-interceptors
  [http/log-request
   (body-params
    (default-parser-map
     :json-options {:key-fn identity}))
   http/json-body
   (interceptors.db/inject-db (db.hairstyle/->DatomicDB conn))])

(def patch-interceptors
  [http/log-request
   (body-params)
   http/json-body
   (interceptors.http/validate-body wire.in/OptionalHairstyle)
   (interceptors.db/inject-db (db.hairstyle/->DatomicDB conn))])

(defn ^:private current-version [_]
  {:status 200
   :body {:version "1.0.0"}})

(defn ^:private get-hairstyles [req]
  {:status 200
   :body (-> (controllers.hairstyle/get-all req)
             adapters.hairstyle/internal-list->wire)})

(defn ^:private get-hairstyle-by-id
  [{{:keys [id]} :path-params :as req}]
  (if-let [result (some-> id
                          (controllers.hairstyle/get-by-id req)
                          adapters.hairstyle/internal->wire)]
    {:status 200
     :body result}
    {:status 404
     :body {:error "Not found"
            :message (str "Hairstyle " id " does not exist")}}))

(defn ^:private insert-hairstyle
  [req]
  (try
    (-> (:json-params req)
        adapters.hairstyle/wire->internal
        (controllers.hairstyle/insert! req))
    {:status 201}
    (catch Exception err
      {:status 500
       :body {:error "Schema mismatch"
              :message (.getMessage err)}})))

(defn ^:private delete-hairstyle [req]
  (let [string-id (get-in req [:path-params :id])
        converted-id (wire.in/id-string->long string-id)]
    (controllers.hairstyle/delete! converted-id req)))

(defn ^:private put-hairstyle [req]
  (let [string-id (get-in req [:path-params :id]) 
        converted-id (wire.in/id-string->long string-id)
        hairstyle-details (:json-params req)
        details-namespaced (wire.in/external->domain hairstyle-details)]
    (controllers.hairstyle/update! converted-id details-namespaced req)))

(defn ^:private patch-hairstyle [req]
  (let [string-id (get-in req [:path-params :id]) 
        converted-id (wire.in/id-string->long string-id)
        hairstyle-details (:json-params req)
        details-namespaced (wire.in/external->domain hairstyle-details)]
    (controllers.hairstyle/patch! converted-id details-namespaced req)))

(def default-routes
  #{["/api/version"
     :get (conj common-interceptors
                current-version)
     :route-name :version]})

(def hairstyle-routes
  #{["/api/hairstyles"
     :get (conj common-interceptors
                get-hairstyles)
     :route-name :get-haistyles]
    ["/api/hairstyle"
     :post (conj body-interceptors
                 insert-hairstyle)
     :route-name :post-haistyle]
    ["/api/hairstyle/:id"
     :get (conj common-interceptors
                get-hairstyle-by-id)
     :route-name :get-by-id]
    ["/api/hairstyle/:id"
     :delete (conj common-interceptors
                   delete-hairstyle)
     :route-name :delete-hairstyle]
    ["/api/hairstyle/:id"
     :put (conj body-interceptors
                put-hairstyle)
     :route-name :put-hairstyle]
    ["/api/hairstyle/:id"
     :patch (conj patch-interceptors
                  patch-hairstyle)
     :route-name :patch-hairstyle]})

(def routes
  (expand-routes
   (set/union
    default-routes
    hairstyle-routes)))