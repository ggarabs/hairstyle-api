(ns hairstyle-api.protocols.datomic)

(defprotocol IDatomic
  (find-all [this]))