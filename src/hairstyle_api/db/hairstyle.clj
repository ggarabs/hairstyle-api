(ns hairstyle-api.db.hairstyle
  (:require [datomic.client.api :as d]
            [hairstyle-api.db.config :refer [conn]]
            [hairstyle-api.protocols.datomic :refer [IDatomic]]))

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

(d/transact conn {:tx-data schema})

(defrecord DatomicDB [conn]
  IDatomic
  (find-all [_this]
    (d/q {:query '[:find (pull ?e [*])
                   :where [?e :hairstyle/name]]
          :args [(d/db conn)]})))