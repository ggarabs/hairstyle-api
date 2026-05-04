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

(defn external->domain [req]
  (update-keys req #(keyword "hairstyle" (name %))))

(defn id-string->long [id]
  (Long/parseLong id))