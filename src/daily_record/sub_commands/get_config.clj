(ns daily-record.sub-commands.get-config
  (:require [daily-record.output :as output]
            [cats.monad.maybe :as maybe]))

(defn command
  [app-context]
  (output/print-structure app-context)
  (maybe/just :okay))
