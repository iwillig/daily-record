(ns daily-record.main
  (:gen-class)
  (:require [cats.core :as monad]
            [clojure.pprint :as pp]
            [bling.banner :as bling.banner]
            [bling.fonts.ansi-shadow :refer [ansi-shadow]]
            [daily-record.db     :as db]
            [daily-record.output :as output]
            [daily-record.config :as config]
            [daily-record.pandoc :as pandoc]))

(set! *warn-on-reflection* true)

(prefer-method pp/simple-dispatch
               clojure.lang.IPersistentMap
               clojure.lang.IDeref)

(System/setProperty "java.awt.headless" "true")

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


(defn handle-monad-result
  [result]
  (println result))


(defn load-config-db
  []
  (monad/mlet [config (config/load-project-config)
               db     (db/connection config)]

    (print-banner)
    (output/callout {::output/type :info
                     ::output/message-str "Loading Config"})
    (output/print-structure config)
    (monad/return [config db])))


(defn -main [& _args]
  (handle-monad-result (load-config-db)))
