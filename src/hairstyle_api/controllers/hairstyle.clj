(ns hairstyle-api.controllers.hairstyle
  (:require [schema.core :as s]
            [hairstyle-api.db.hairstyle :as db]
            [hairstyle-api.wire.out.hairstyle :refer [domain->external]]))

(s/defn get-all 
  [{:keys [datomic]}]
    (let [results (db/find-all datomic)]
      {:status 200
       :body (mapv domain->external results)}))

(s/defn get-by-id
  [id {:keys [datomic]}]
  (if-let [maybe-hairstyle (domain->external (db/find-by-id id datomic))]
    {:status 200
     :body maybe-hairstyle}
    {:status 404
     :body {:error "Not found"
            :message (str "Hairstyle " id " does not exist")}}))

(s/defn insert!
  [details {:keys [datomic]}]
  (db/upsert! details datomic)
  {:status 201})

(s/defn delete!
  [id {:keys [datomic]}]
  (if-let [_ (db/find-by-id id datomic)]
    (do
      (db/retract! id datomic)
      {:status 204})
    {:status 404
     :body {:error "Not found"
            :message (str "Hairstyle " id " does not exist")}}))

(s/defn update!
  [id details {:keys [datomic]}]
  (if-let [_ (db/find-by-id id datomic)]
    (do
      (db/update! id details datomic)
      {:status 200
       :body (domain->external (db/find-by-id id datomic))})
    {:status 404
     :body {:error "Not found"
            :message (str "Hairstyle " id " does not exist")}}))

(s/defn patch!
  [id details {:keys [datomic]}]
  (if-let [_ (db/find-by-id id datomic)]
    (do
      (db/upsert! id details datomic)
      {:status 200
       :body (domain->external (db/find-by-id id datomic))})
    {:status 404
     :body {:error "Not found"
            :message (str "Hairstyle " id " does not exist")}}))