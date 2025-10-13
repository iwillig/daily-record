(ns daily-record.output
  (:require [bling.core :as bling]
            [clojure.pprint :as pp]
            [bling.explain :as maill.explain]
            [bling.hifi :as hifi]))

(defn print-table
  [])

(defn callout
  [{::keys [type message-str]}]
  (apply bling/callout [{:type type} message-str]))

(defn print-structure
  [structure]
  (pp/pprint structure)
  ;; Print hifi does not seem to work
  #_(hifi/print-hifi structure))
