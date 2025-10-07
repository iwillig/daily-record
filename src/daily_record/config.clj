(ns daily-record.config
  (:require [malli.core :as m]
            [clj-yaml.core :as yaml]
            [cats.core :as monad]
            [cats.monad.exception :as monad.exception]
            [clojure.java.io :as io]))

(def ConfigInfoSpec
  [:map {:closed true}
   [:days-folder    :string]
   [:source         [:enum :file :default]]
   [:db-path        :string]
   [:templates-path :string]])

(defrecord ConfigInfo [days-folder source db-path templates-path])

(defrecord ConfigPathInfo [config-file config-dir])

(def home-dir (System/getProperty "user.home"))

(def default-config-path
  [home-dir ".config" "daily-record"])

(def default-config-file-name "config.yml")

(def default-config-file-path
  (into default-config-path [default-config-file-name]))

(defn file-from-path-seq
  [file-path-seq]
  (apply io/file file-path-seq))

(defn ensure-folder
  [config-path]
  (let [file (file-from-path-seq config-path)]
    (when-not (.exists file)
     (.mkdirs file))))

(defn parse-yaml-string
  [string]
  (monad.exception/try-on
   (yaml/parse-string string)))

(defn load-yaml-file
  [yaml-file-path]
  (-> (file-from-path-seq yaml-file-path)
      (slurp)
      (parse-yaml-string)))

(defn load-config
  ([]
   (load-config default-config-file-path))
  ([config-file-path]
   (monad/mlet [config-info (load-yaml-file config-file-path)]
     (monad/return (map->ConfigInfo config-info)))))

(defn detect-config-path
  [])

(defn load-project-config
  []
  (load-config))
