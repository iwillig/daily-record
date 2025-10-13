(ns daily-record.db
  (:require [datalevin.core :as d]
            [cats.monad.exception :as monad.exception]
            [daily-record.config :as config]))

(def schema
  {:document/file-path {:db/unique      :db.unique/identity}
   :document/root-node {:db/valueType   :db.type/ref
                        :db/isComponent true}
   :node/children      {:db/valueType   :db.type/ref
                        :db/cardinality :db.cardinality/many
                        :db/isComponent true}
   :node/parent        {:db/valueType   :db.type/ref}
   :node/tag-names     {:db/cardinality :db.cardinality/many}})


(defn connection
  [config]
  (monad.exception/try-on
   #_(throw (ex-info "Something went wrong" {}))
   (d/get-conn
    (config/db-path-string config)
    schema)))
