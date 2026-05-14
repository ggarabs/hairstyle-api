(ns hairstyle-api.interceptors.cors)

(def cors-headers
  {"Access-Control-Allow-Origin"  "*"
   "Access-Control-Allow-Methods" "GET"
   "Access-Control-Allow-Headers" "Content-Type"
   "Access-Control-Allow-Credentials" "true"})

(defn add-cors-headers [response]
  (update response :headers merge cors-headers))

(def cors-interceptor
  {:name ::cors
   :enter
   (fn [context]
     (let [request (:request context)]
       (if (= :options (:request-method request))
         ;; resposta para preflight
         (assoc context
                :response
                {:status 200
                 :headers cors-headers
                 :body ""})
         context)))

   :leave
   (fn [context]
     (update context :response add-cors-headers))})