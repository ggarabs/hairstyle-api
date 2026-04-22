(ns hairstyle-api.db.hairstyle
  (:require [datomic.api :as d]
            [hairstyle-api.db.config :refer [conn]]
            [hairstyle-api.protocols.datomic :refer [IDatomic db]]
            [schema.core :as s]))

(def schema
  [{:db/ident :hairstyle/name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident :haistyle/slug
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

(s/defn find-all [datomic]
  (d/q '[:find (pull ?e [*])
         :in $
         :where [?e :hairstyle/name]]
       (db datomic)))

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