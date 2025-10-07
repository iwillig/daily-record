(ns daily-record.main
  (:gen-class)
  (:require [datalevin.core :as d]
            [cats.core :as monad]
            [clojure.pprint :as pp]
            [bling.banner :as bling.banner]
            [bling.fonts.ansi-shadow :refer [ansi-shadow]]
            [daily-record.output :as output]
            [daily-record.config :as config]
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


(defn banner
  []
  (bling.banner/banner
   {:font               ansi-shadow
    :text               "Daily Record"
    :gradient-colors    [:purple :orange]
    :gradient-direction :to-right}))

(defn print-banner
  []
  (println)
  (println (banner))
  (println))

(defn -main [& _args]
  (monad/mlet [config (config/load-project-config)]
    (print-banner)
    (output/callout {::output/type :info
                     ::output/message-str "Loading Config"})
    (output/print-structure config)))
