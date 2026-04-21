(ns hairstyle-api.components
  (:require [io.pedestal.http :as http]
            [hairstyle-api.diplomat.http-server :as diplomat.http-server]))

(def service
  {::http/routes diplomat.http-server/routes
   ::http/type :jetty
   ::http/port 8080})

(defn create-and-start-system! []
    (-> service
        http/default-interceptors
        http/create-server
        http/start))