(ns hairstyle-api.controllers.hairstyle
  (:require [schema.core :as s]
            [hairstyle-api.db.hairstyle :as db]))

(s/defn get-all 
  [{:keys [datomic]}]
  {:status 200
   :body (db/find-all datomic)})

(s/defn get-by-id
  [id {:keys [datomic]}]
  (if-let [maybe-hairstyle (db/find-by-id id datomic)]
    {:status 200
     :body maybe-hairstyle}
    {:status 404
     :body {:error "Not found"
            :message (str "Hairstyle " id " does not exist")}}))

(s/defn insert!
  [details {:keys [datomic]}]
  (db/insert! details datomic)
  {:status 201})