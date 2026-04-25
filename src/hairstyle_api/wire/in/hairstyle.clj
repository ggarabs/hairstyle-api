(ns hairstyle-api.wire.in.hairstyle)

(defn external->domain [req]
  (update-keys req #(keyword "hairstyle" (name %))))

(defn id-string->long [id]
  (Long/parseLong id))