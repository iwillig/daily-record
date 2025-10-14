(ns daily-record.main
  (:gen-class)
  (:require
            [clojure.pprint :as pp]
            [bling.banner :as bling.banner]
            [bling.fonts.ansi-shadow :refer [ansi-shadow]]
            [daily-record.context :as context]
            [daily-record.db      :as db]
            [daily-record.output  :as output]
            [daily-record.config  :as config]
            [daily-record.sub-commands.start-day :as start-day]
            [daily-record.sub-commands.end-day   :as end-day]
            [cli-matic.core :as cli]
            [cats.core :as monad]
            [cats.monad.either :as either]
            [cats.monad.maybe :as maybe]))

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
  (cond
    (maybe/maybe? result)
    (if (maybe/nothing? result)
      1 0)
    (either/either? result)
    (either/branch result
                   (constantly 1)
                   (constantly 0))
    (nil? result) 1
    :else 0))

(defn print-hello
  [config]
  (print-banner)
  (output/callout {::output/type :info
                   ::output/message-str "Loading Config"})
  (output/print-structure config))

(defn load-config-db
  []
  (monad/mlet [config (config/load-project-config)
               db     (db/connection config)]
    (monad/return
     (context/map->Context {:db db :config config}))))

(def config-cli
  {:command     "daily-record"
   :description "A command line tool for managing your day"
   :version     "0.0.1"
   :subcommands [{:command     "start-day"
                  :description "A command to start your day"
                  :runs        start-day/command}

                 {:command     "end-day"
                  :description "A command to end your day"
                  :runs        end-day/command}]})

(defn -main [& args]
  (cli/run-cmd args config-cli))
