(ns hairstyle-api.wire.in.hairstyle
  (:require [schema.core :as s]))

(s/defschema hairstyle
  {:name s/Str
   (s/optional-key :slug) s/Str
   :description s/Str
   :texture [s/Str]
   :length s/Str
   :main-image s/Str
   (s/optional-key :gallery) [s/Str]
   (s/optional-key :tags) [s/Str]})

(s/defschema optional-hairstyle
  {(s/optional-key :name) s/Str
   (s/optional-key :slug) s/Str
   (s/optional-key :description) s/Str
   (s/optional-key :texture) [s/Str]
   (s/optional-key :length) s/Str
   (s/optional-key :main-image) s/Str
   (s/optional-key :gallery) [s/Str]
   (s/optional-key :tags) [s/Str]})

(defn external->domain [req]
  (update-keys req #(keyword "hairstyle" (name %))))

(defn id-string->long [id]
  (Long/parseLong id))