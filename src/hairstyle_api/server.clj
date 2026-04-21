(ns hairstyle-api.server
  (:require [hairstyle-api.components :as components]
            [schema.core :as s]))

(s/set-fn-validation! true)

(defn -main []
  (println "Server running on port 8080...")
  (components/create-and-start-system!))