(ns hairstyle-api.db.hairstyle
  (:require [clojure.set :as set]
            [datomic.api :as d]
            [hairstyle-api.db.config :refer [conn]]
            [hairstyle-api.protocols.datomic :refer [IDatomic db] :as db]
            [hairstyle-api.adapters.hairstyle :as adapters.hairstyle]
            [hairstyle-api.models.hairstyle :as models.hairstyle]
            [hairstyle-api.db.schema.hairstyle :as db.schema.hairstyle]
            [schema.core :as s]))

@(d/transact conn db.schema.hairstyle/schema)

(s/defn find-all :- models.hairstyle/List
  [datomic]
  (let [conn (:conn datomic) 
        db   (d/db conn)] 
    (->> (d/q '[:find [?e ...]
                :where [?e :hairstyle/name]] 
              db)
         (mapv #(d/pull db '[*] %))
         adapters.hairstyle/db->internal-list)))

(s/defn find-by-id :- (s/maybe models.hairstyle/Hairstyle)
  [id :- s/Str
   datomic]
  (some->> (d/q '[:find (pull ?e [*]) .
                  :in $ ?e
                  :where
                  [?e :hairstyle/name _]]
                (db datomic)
                (Long/parseLong id))
           adapters.hairstyle/db->internal))

(s/defn upsert!
  ([hairstyle :- models.hairstyle/Create
    datomic]
   (let [namespaced-hairstyle (adapters.hairstyle/internal->db hairstyle)]
     (db/transact datomic [namespaced-hairstyle] {})))

  ([id :- s/Str
    hairstyle :- models.hairstyle/OptionalHairstyle
    datomic]
   (let [changes (-> hairstyle
                     adapters.hairstyle/internal-changes->db
                     (assoc :db/id (Long/parseLong id)))]
     (db/transact datomic [changes] {}))))

(s/defn retract!
  [id :- s/Str
   datomic]
  (db/transact datomic [[:db.fn/retractEntity (Long/parseLong id)]] {}))

(s/defn update!
  [id :- s/Str 
   hairstyle :- models.hairstyle/Create
   datomic]
    (let [eid (Long/parseLong id)
          namespaced-hairstyle (adapters.hairstyle/internal->db hairstyle)
          current (d/pull (db datomic) '[*] eid)
          current-fields (dissoc current :db/id)
          as-set (fn [v] (if (coll? v) (set v) #{v}))
          retractions (vec (for [[k v] current-fields
                                 :let [new-val (get namespaced-hairstyle k)
                                       to-retract (set/difference (as-set v)
                                                                  (as-set new-val))]
                                 val to-retract]
                             [:db/retract eid k val]))
          with-id (assoc namespaced-hairstyle :db/id eid)
          tx-data (conj retractions with-id)]
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