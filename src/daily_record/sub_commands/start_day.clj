(ns daily-record.sub-commands.start-day
  "Command to start a day record."
  (:require [daily-record.output :as output]
            [bblgum.core :as b]))

(defn command
  [& args]
  (output/callout {::output/type "info"
                   ::output/message-str "What is your focus for the day?"})
  (let [focus (b/gum :input :placeholder "Focus")]
    (println args focus)))
