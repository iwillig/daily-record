(ns daily-record.sub-commands.start-day
  (:require [daily-record.output :as output]
            [bblgum.core :as b]))

(defn command
  [& args]
  (let [focus (b/gum :input :placeholder "What is your focus for the day?")]
    (println args focus)))
