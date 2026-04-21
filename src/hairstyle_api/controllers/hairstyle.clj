(ns hairstyle-api.controllers.hairstyle
  (:require [schema.core :as s]
            [hairstyle-api.protocols.datomic :refer [find-all]]))

(s/defn get-all 
  [{:keys [datomic]}]
  {:status 200
   :body (find-all datomic)})