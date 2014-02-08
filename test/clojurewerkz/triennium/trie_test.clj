(ns clojurewerkz.triennium.trie-test
  (:require [clojure.test :refer :all]
            [clojurewerkz.triennium.trie :as tr]))

(deftest test-make-trie
  (let [t (tr/make-trie)]
    (is (= t {} tr/empty-trie))))

