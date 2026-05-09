(ns hairstyle-api.wire.out.hairstyle
  (:require [schema.core :as s]))

(defn domain->external [entity]
  (reduce-kv (fn [m k v]
               (let [new-key (if (keyword? k) (name k) (str k))
                     final-key (if (= new-key "id") "id" new-key)]
                 (assoc m final-key (if (set? v) (vec v) v))))
             {}
             entity))

(s/defschema Hairstyle
  {(s/required-key "id") s/Num
   (s/required-key "name") s/Str
   (s/optional-key "slug") s/Str
   (s/required-key "description") s/Str
   (s/required-key "texture") [s/Str]
   (s/required-key "length") s/Str
   (s/required-key "mainImage") s/Str
   (s/optional-key "gallery") [s/Str]
   (s/optional-key "tags") [s/Str]})

(s/defschema List
  [Hairstyle])