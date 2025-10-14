(ns daily-record.main-test
  (:require [clojure.test :as t]
            [matcher-combinators.test]))

(t/deftest testokay
  (t/testing "testing"
    (t/is (match? {} #{}))))
