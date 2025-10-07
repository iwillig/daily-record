(ns daily-record.output
  (:require [bling.core :as bling]
            [bling.explain :as maill.explain]
            [bling.hifi :as hifi]))

(defn print-table
  [])

(defn callout
  [{::keys [type message-str]}]
  (bling/callout {:type type} message-str))

(defn print-structure
  [structure]
  (hifi/print-hifi structure))
