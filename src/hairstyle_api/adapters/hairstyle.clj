(ns hairstyle-api.adapters.hairstyle
  (:require [hairstyle-api.wire.out.hairstyle :as wire.out.hairstyle]
            [hairstyle-api.wire.db.hairstyle :as wire.db.hairstyle]
            [hairstyle-api.models.hairstyle :as models.hairstyle]
            [hairstyle-api.wire.in.hairstyle :as wire.in.hairstyle]
            [clojure.string :as str]
            [schema.core :as s]))

(s/defn ^:private kebab->camel :- s/Str
  [s :- s/Str]
  (let [[first-part & rest-parts] (str/split s #"-")]
    (apply str first-part (map str/capitalize rest-parts))))

(s/defn ^:private camel->kebab :- s/Str
  [s :- s/Str]
  (reduce
   (fn [acc ch]
     (if (Character/isUpperCase ch)
       (str acc \- (Character/toLowerCase ch))
       (str acc ch)))
   ""
   s))

(s/defn db->internal :- models.hairstyle/Hairstyle
  [hairstyle :- wire.db.hairstyle/Persisted]
  (update-keys hairstyle #(keyword (name %))))

(s/defn db->internal-list :- models.hairstyle/List
  [hairstyle-list :- wire.db.hairstyle/List]
  (map db->internal hairstyle-list))

(s/defn internal->db :- wire.db.hairstyle/Create
  [hairstyle :- models.hairstyle/Create]
  (update-keys hairstyle #(keyword (str "hairstyle/" (name %)))))

(s/defn internal->wire :- wire.out.hairstyle/Hairstyle
  [hairstyle :- models.hairstyle/Hairstyle]
  (update-keys hairstyle #(kebab->camel (name %))))

(s/defn internal-list->wire :- wire.out.hairstyle/List
  [hairstyle-list :- models.hairstyle/List]
  (map internal->wire hairstyle-list))

(s/defn wire->internal :- models.hairstyle/Create
  [external-hairstyle :- wire.in.hairstyle/Hairstyle]
  (update-keys external-hairstyle #(keyword (camel->kebab %))))