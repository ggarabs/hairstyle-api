(ns hairstyle-api.wire.out.hairstyle
  (:require [schema.core :as s]))

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