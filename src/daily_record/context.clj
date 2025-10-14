(ns daily-record.context
  "Namespace to capture the core Context object")

(defrecord Context [db config])

(alter-meta! #'->Context assoc :doc "The primary app context object")

(comment
  (require '[clojure.repl :as repl])
  (repl/doc ->Context))
