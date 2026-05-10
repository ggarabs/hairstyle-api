(ns hairstyle-api.controllers.hairstyle
  (:require [schema.core :as s]
            [hairstyle-api.db.hairstyle :as db]
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
  [details :- models.hairstyle/Create
   id :- s/Str
   {:keys [datomic]}]
  (when (db/find-by-id id datomic)
    (db/update! id details datomic)
    (db/find-by-id id datomic)))

(s/defn patch!
  [changes :- models.hairstyle/OptionalHairstyle
   id :- s/Str
   {:keys [datomic]}]
  (when (db/find-by-id id datomic)
    (db/upsert! id changes datomic)
    (db/find-by-id id datomic)))