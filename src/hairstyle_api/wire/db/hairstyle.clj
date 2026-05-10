(ns hairstyle-api.wire.db.hairstyle
  (:require [schema.core :as s]))

(s/defschema Create
  {:hairstyle/name s/Str
   (s/optional-key :hairstyle/slug) s/Str
   :hairstyle/description s/Str
   :hairstyle/texture [s/Str]
   :hairstyle/length s/Str
   :hairstyle/main-image s/Str
   (s/optional-key :hairstyle/gallery) [s/Str]
   (s/optional-key :hairstyle/tags) [s/Str]})

(s/defschema PersistedChanges
  {(s/optional-key :hairstyle/name) s/Str
   (s/optional-key :hairstyle/slug) s/Str
   (s/optional-key :hairstyle/description) s/Str
   (s/optional-key :hairstyle/texture) [s/Str]
   (s/optional-key :hairstyle/length) s/Str
   (s/optional-key :hairstyle/main-image) s/Str
   (s/optional-key :hairstyle/gallery) [s/Str]
   (s/optional-key :hairstyle/tags) [s/Str]})

(s/defschema Persisted
  {:db/id s/Num
   :hairstyle/name s/Str
   (s/optional-key :hairstyle/slug) s/Str
   :hairstyle/description s/Str
   :hairstyle/texture [s/Str]
   :hairstyle/length s/Str
   :hairstyle/main-image s/Str
   (s/optional-key :hairstyle/gallery) [s/Str]
   (s/optional-key :hairstyle/tags) [s/Str]})

(s/defschema List
  [Persisted])