(ns hairstyle-api.diplomat.http-server 
  (:require
   [clojure.set :as set]
   [io.pedestal.http :as http]
   [io.pedestal.http.body-params :refer [body-params]]
   [io.pedestal.http.route :refer [expand-routes]]
   [hairstyle-api.controllers.hairstyle :as controllers.hairstyle]
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

(def default-routes
  #{["/api/version"
     :get (conj common-interceptors
                current-version)
     :route-name :version]})

(def hairstyle-routes
  #{["/api/hairstyles"
     :get (conj common-interceptors
                get-hairstyles)
     :route-name :get-haistyles]})

(def routes
  (expand-routes
   (set/union
    default-routes
    hairstyle-routes)))