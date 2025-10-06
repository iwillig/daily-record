(ns daily-record.main
  (:gen-class)
  (:require [datalevin.core :as d]
            [clojure.pprint :as pp]
            [daily-record.pandoc :as pandoc]))

(set! *warn-on-reflection* true)

(prefer-method pp/simple-dispatch
               clojure.lang.IPersistentMap
               clojure.lang.IDeref)

(System/setProperty "java.awt.headless" "true")

(def schema
  {:document/file-path {:db/unique :db.unique/identity}
   :document/root-node {:db/valueType   :db.type/ref
                        :db/isComponent true}
   :node/children      {:db/valueType   :db.type/ref
                        :db/cardinality :db.cardinality/many
                        :db/isComponent true}
   :node/parent        {:db/valueType :db.type/ref}
   :node/tag-names     {:db/cardinality :db.cardinality/many}})



(defn -main [& args]
  (let [conn (d/get-conn "/tmp/datalevin/mydb" schema)]
    (println conn)
    (println (pandoc/pandoc-installed?))
    (System/exit 0)))
