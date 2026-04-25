(ns hairstyle-api.controllers.hairstyle
  (:require [schema.core :as s]
            [hairstyle-api.db.hairstyle :as db]
            [clojure.pprint :refer :all]))

(s/defn get-all 
  [{:keys [datomic]}]
  {:status 200
   :body (db/find-all datomic)})

(s/defn insert!
  [details {:keys [datomic]}]
  (db/insert! details datomic)
  {:status 201})