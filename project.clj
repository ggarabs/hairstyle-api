(defproject hairstyle-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [io.pedestal/pedestal.service "0.6.0"]
                 [io.pedestal/pedestal.jetty "0.6.0"]
                 [com.datomic/peer "1.0.7556"]
                 [prismatic/schema "1.4.1"]]
  :main ^:skip-aot hairstyle-api.server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
