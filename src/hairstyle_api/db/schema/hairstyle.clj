(ns hairstyle-api.db.schema.hairstyle)

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

   #_{:db/ident :hairstyle/rating
    :db/valueType :db.type/float
    :db/cardinality :db.cardinality/one}])