(ns hairstyle-api.wire.in.hairstyle)

(defn external->domain [req]
  (update-keys req #(keyword "hairstyle" (name %))))