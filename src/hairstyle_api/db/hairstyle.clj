(ns hairstyle-api.db.hairstyle
  (:require [datomic.api :as d]
            [hairstyle-api.db.config :refer [conn]]
            [hairstyle-api.protocols.datomic :refer [IDatomic db] :as db]
            [schema.core :as s]))

(def schema
  [{:db/ident :hairstyle/name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident :hairstyle/slug
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident :hairstyle/description
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident :hairstyle/texture
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/many}

   {:db/ident :hairstyle/length
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident :hairstyle/main-image
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident :hairstyle/gallery
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/many}

   {:db/ident :hairstyle/tags
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/many}

   {:db/ident :hairstyle/rating
    :db/valueType :db.type/float
    :db/cardinality :db.cardinality/one}])

@(d/transact conn schema)

(defn find-all [datomic]
  (let [conn (:conn datomic) 
        db   (d/db conn)] 
    (->> (d/q '[:find [?e ...]
                :where [?e :hairstyle/name]] 
              db)
         (mapv #(d/pull db '[*] %)))))

(s/defn find-by-id [id datomic]
  (d/q '[:find (pull ?id [*]) .
         :in $ ?id
         :where [?id :hairstyle/name _]]
       (db datomic) id))

(s/defn upsert!
  ([hairstyle datomic]
   (db/transact datomic [hairstyle] {}))
  ([id hairstyle datomic]
   (let [with-id (assoc hairstyle :db/id id)]
     (db/transact datomic [with-id] {}))))

(s/defn retract!
  [id datomic]
  (db/transact datomic [[:db.fn/retractEntity id]] {}))

(s/defn update!
  [id hairstyle datomic]
    (let [current (find-by-id id datomic)
          current-fields (dissoc current :db/id)
          retractions (vec (for [[k v] current-fields
                                 val (if (coll? v) v [v])]
                             [:db/retract id k val]))
          with-id (assoc hairstyle :db/id id)
          tx-data (conj (vec retractions) with-id)]
      (db/transact datomic tx-data {})))

(defrecord DatomicDB [conn]
  IDatomic
  (transact [_this datoms _options]
    @(d/transact conn datoms))
  (db [_this]
    (d/db conn))
  (filtered-db [_this pred]
    (d/filter (d/db conn) pred))
  (conn [_this]
    conn))