(ns daily-record.pandoc
  (:require [cats.core :as monad]
            [clojure.zip :as zip]
            [cats.monad.exception :as monad.exception]
            [cats.monad.either :as monad.either]
            [clojure.data.json :as json]
            [clojure.java.shell :as shell]))

(defn shell
  [args]
  (let [results (apply shell/sh args)
        result-fn (case (:exit results)
                    0 monad.either/right
                    monad.either/left)]
    (result-fn results)))

(defn pandoc-installed?
  []
  (shell ["pandoc" "--version"]))

(defn build-pandoc-command
  [in-format out-format string-content]
  ["pandoc" "-f" (name in-format) "-t" (name out-format) :in string-content])

(defn string->pandoc-ast
  [string-content]
  (monad/mlet [result (shell (build-pandoc-command :org :json string-content))
               json   (monad.exception/try-on
                       (json/read-str (:out result)
                                      :key-fn keyword))]
    (monad/return json)))

(defn branch?
  [node]
  (or (map? node) (sequential? node)))

(defn childern
  [node]
  (cond
    (map? node) (vals node)
    (sequential? node) (seq node)))

(defn build-node
  [node childern]
  (cond
    (map? node) (into (empty node) (map vector (keys node) childern))
    (sequential? node) (into (empty node) childern)))

(defrecord Node [t c])

(defn zip-pandoc
  [blocks]
  (let [zipper (zip/zipper branch? childern build-node blocks)]
    (loop [loc     zipper
           results []]
      (if (zip/end? loc)
        results
        (let [new-node (zip/node loc)
              parent   (zip/up loc)]
          (recur (zip/next loc) (conj results {:node   new-node
                                               :parent parent})))))))
