(ns hairstyle-api.models.hairstyle
  (:require [schema.core :as s]))

(s/defschema Hairstyle
  {:id s/Num
   :name s/Str
   (s/optional-key :slug) s/Str
   :description s/Str
   :texture [s/Str]
   :length s/Str
   :main-image s/Str
   (s/optional-key :gallery) [s/Str]
   (s/optional-key :tags) [s/Str]})

(s/defschema List
  [Hairstyle])