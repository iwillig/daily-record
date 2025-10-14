(ns daily-record.template
  (:require [clostache.parser :as mustache]
            [cats.monad.exception :as monad.exc]))

(defrecord TemplateContext [date])

(defn render-template
  [template-path template-context]
  (monad.exc/try-on
    (mustache/render
      (slurp template-path)
      template-context)))
