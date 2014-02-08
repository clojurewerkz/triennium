(ns clojurewerkz.triennium.mqtt-test
  (:require [clojure.test :refer :all]
            [clojurewerkz.triennium.mqtt :as tr]))

(deftest test-split-topic
  (are [topic words] (is (= (tr/split-topic topic) words))
    "a/b/c" ["a" "b" "c"]
    "a/b/+/d" ["a" "b" "+" "d"]
    "a/b/c/#" ["a" "b" "c" "#"]))
