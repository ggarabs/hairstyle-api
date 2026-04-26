(ns hairstyle-api.diplomat.http-server 
  (:require
   [clojure.set :as set]
   [io.pedestal.http :as http]
   [io.pedestal.http.body-params :refer [body-params]]
   [io.pedestal.http.route :refer [expand-routes]]
   [hairstyle-api.controllers.hairstyle :as controllers.hairstyle]
   [hairstyle-api.wire.in.hairstyle :as wire.in.hairstyle]
   [hairstyle-api.db.config :refer [conn]]
   [hairstyle-api.db.hairstyle :as db.hairstyle]))

(defn inject-db [datomic-db]
  {:name  ::inject-db
   :enter (fn [context]
            (assoc-in context [:request :datomic] datomic-db))})

(def common-interceptors
  [http/log-request
   (body-params)
   http/json-body
   (inject-db (db.hairstyle/->DatomicDB conn))])

(defn current-version [_]
  {:status 200
   :body {:version "1.0.0"}})

(defn get-hairstyles [req]
  (controllers.hairstyle/get-all req))

(defn get-hairstyle-by-id [req]
  (let [string-id (get-in req [:path-params :id])
        converted-id (wire.in.hairstyle/id-string->long string-id)]
    (controllers.hairstyle/get-by-id converted-id req)))

(defn insert-hairstyle [req]
  (let [hairstyle-details (:json-params req)
        details-namespaced (wire.in.hairstyle/external->domain hairstyle-details)]
    (controllers.hairstyle/insert! details-namespaced req)))

(defn delete-hairstyle [req]
  (let [string-id (get-in req [:path-params :id])
        converted-id (wire.in.hairstyle/id-string->long string-id)]
    (controllers.hairstyle/delete! converted-id req)))

(defn put-hairstyle [req]
  (let [string-id (get-in req [:path-params :id]) 
        converted-id (wire.in.hairstyle/id-string->long string-id)
        hairstyle-details (:json-params req)
        details-namespaced (wire.in.hairstyle/external->domain hairstyle-details)]
    (controllers.hairstyle/update! converted-id details-namespaced req)))

(defn patch-hairstyle [req]
  (let [string-id (get-in req [:path-params :id]) 
        converted-id (wire.in.hairstyle/id-string->long string-id)
        hairstyle-details (:json-params req)
        details-namespaced (wire.in.hairstyle/external->domain hairstyle-details)]
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
     :post (conj common-interceptors
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
     :put (conj common-interceptors
                put-hairstyle)
     :route-name :put-hairstyle]
    ["/api/hairstyle/:id"
     :patch (conj common-interceptors
                  patch-hairstyle)
     :route-name :patch-hairstyle]})

(def routes
  (expand-routes
   (set/union
    default-routes
    hairstyle-routes)))