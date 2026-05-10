(ns hairstyle-api.controllers.hairstyle
  (:require [schema.core :as s]
            [hairstyle-api.db.hairstyle :as db]
            [hairstyle-api.wire.out.hairstyle :refer [domain->external]]
            [hairstyle-api.models.hairstyle :as models.hairstyle]))

(s/defn get-all
  [{:keys [datomic]}]
    (db/find-all datomic))

(s/defn get-by-id
  [id :- s/Str
   {:keys [datomic]}]
  (db/find-by-id id datomic))

(s/defn insert!
  [details :- models.hairstyle/Create
   {:keys [datomic]}]
  (db/upsert! details datomic))

(s/defn delete!
  [id :- s/Str
   {:keys [datomic]}]
    (when (db/find-by-id id datomic)
      (db/retract! id datomic)))

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