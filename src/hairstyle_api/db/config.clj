(ns hairstyle-api.db.config
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/hairstyle-db")

(d/create-database db-uri)

(def conn
  (d/connect db-uri))