(ns hairstyle-api.wire.in.hairstyle
  (:require [schema.core :as s]))

(s/defschema Hairstyle
  {(s/required-key "name") s/Str
   (s/optional-key "slug") s/Str
   (s/required-key "description") s/Str
   (s/required-key "texture") [s/Str]
   (s/required-key "length") s/Str
   (s/required-key "mainImage") s/Str
   (s/optional-key "gallery") [s/Str]
   (s/optional-key "tags") [s/Str]})

(s/defschema OptionalHairstyle
  {(s/optional-key "name") s/Str
   (s/optional-key "slug") s/Str
   (s/optional-key "description") s/Str
   (s/optional-key "texture") [s/Str]
   (s/optional-key "length") s/Str
   (s/optional-key "mainImage") s/Str
   (s/optional-key "gallery") [s/Str]
   (s/optional-key "tags") [s/Str]})

(defn external->domain [req]
  (update-keys req #(keyword "hairstyle" (name %))))

(defn id-string->long [id]
  (Long/parseLong id))