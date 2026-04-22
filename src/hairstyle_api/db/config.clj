(ns hairstyle-api.db.config
  (:require [datomic.api :as d]))

(def db-uri "datomic:mem://hairstyle-db")

(d/create-database db-uri)

(def conn
  (d/connect db-uri))