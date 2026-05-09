(ns hairstyle-api.interceptors.http
  (:require [schema.core :as s]
            [io.pedestal.interceptor.chain :as chain]))

(defn validate-body [schema]
  {:name ::validate-body
   :enter
   (fn [context]
     (let [body (or (get-in context [:request :json-params]) {})
           errors (s/check schema body)]
       (if errors
         (-> context
             (assoc :response {:status 400
                               :body {:errors errors}}) 
             chain/terminate) 
         context)))})