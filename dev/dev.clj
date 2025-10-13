(ns dev
  (:require
   [kaocha.repl :as k]
   [clj-reload.core :as reload]))

(reload/init
  {:dirs ["src" "dev" "test"]})

(defn refresh
  []
  (reload/reload))
