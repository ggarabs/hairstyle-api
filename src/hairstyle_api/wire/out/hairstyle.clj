(ns hairstyle-api.wire.out.hairstyle)

(defn domain->external [entity]
  (reduce-kv (fn [m k v]
               (let [new-key (if (keyword? k) (name k) (str k))
                     final-key (if (= new-key "id") "id" new-key)]
                 (assoc m final-key (if (set? v) (vec v) v))))
             {}
             entity))