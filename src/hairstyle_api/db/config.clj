(ns hairstyle-api.db.config
  (:require [datomic.client.api :as d]))

(def client
  (d/client {:server-type :dev-local
             :system "hairstyle-api"
             :storage-dir :mem}))

(d/create-database client {:db-name "hairstyle-db"})

(def conn
  (d/connect client {:db-name "hairstyle-db"}))