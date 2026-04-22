(ns hairstyle-api.protocols.datomic)

(defprotocol IDatomic
  (transact [component datoms options])
  (db [component])
  (filtered-db [component] [component args])
  (conn [component]))