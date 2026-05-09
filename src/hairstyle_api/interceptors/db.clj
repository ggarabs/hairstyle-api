(ns hairstyle-api.interceptors.db)

(defn inject-db [datomic-db]
  {:name  ::inject-db
   :enter (fn [context]
            (assoc-in context [:request :datomic] datomic-db))})
