(ns clojurewerkz.triennium.trie-test
  (:require [clojure.test :refer :all]
            [clojurewerkz.triennium.trie :as tr]))

(deftest test-make-trie
  (let [t (tr/make-trie)]
    (is (= t {} tr/empty-trie))))

(deftest test-insert
  (testing "case 1"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a"] :a))]
      (is (= t2 {"a" {:values #{:a}}}))))
  (testing "case 2"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a"] :a)
                 (tr/insert ["b"] :b))]
      (is (= t2 {"a" {:values #{:a}} "b" {:values #{:b}}}))))
  (testing "case 3"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a"] :a)
                 (tr/insert ["b" "c"] :c))]
      (is (= t2 {"a" {:values #{:a}} "b" {"c" {:values #{:c}}}}))))
  (testing "case 4"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a" "b"] :a)
                 (tr/insert ["b" "c"] :c))]
      (is (= t2 {"a" {"b" {:values #{:a}}} "b" {"c" {:values #{:c}}}}))))
  (testing "case 5"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a" "b"] :a)
                 (tr/insert ["a" "b" "c"] :c))]
      (is (= t2 {"a" {"b" {:values #{:a} "c" {:values #{:c}}}}}))))
  (testing "case 6"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a" "b" "c" "d"] :a)
                 (tr/insert ["a" "b" "c" "d" "*"] :b))]
      (is (= t2 {"a" {"b" {"c" {"d" {:values #{:a} "*" {:values #{:b}}}}}}}))))
  (testing "case 7"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a" "b" "c" "d" "#"] :a)
                 (tr/insert ["a" "b" "c" "d" "*"] :b))]
      (is (= t2 {"a" {"b" {"c" {"d" {"*" {:values #{:b}} "#" {:values #{:a}}}}}}}))))
  (testing "case 8"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert ["a" "b" "c"] :a)
                 (tr/insert ["a" "b" "c" "d" "*"] :b)
                 (tr/insert ["#" "info"] :c)
                 (tr/insert ["weather" "storm" "*"] :d))]
      (is (= t2 {"weather" {"storm" {"*" {:values #{:d}}}},
                 "#" {"info" {:values #{:c}}},
                 "a" {"b" {"c" {:values #{:a} "d" {"*" {:values #{:b}}}}}}})))))

(deftest test-delete
  (testing "case 1"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a"] :a)
                (tr/delete ["a"] :a))]
      (is (= {} t))))
  (testing "case 2"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a"] :a)
                (tr/insert ["b"] :b)
                (tr/insert ["c"] :c)
                (tr/delete ["a"] :a))]
      (is (= {"b" {:values #{:b}} "c" {:values #{:c}}} t))))
  (testing "case 3"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a" "b" "c"] :a)
                (tr/insert ["a" "b" "d"] :b)
                (tr/insert ["a" "c"] :c)
                (tr/delete ["a" "b" "c"] :a))]
      (is (= {"a" {"c" {:values #{:c}}, "b" {"d" {:values #{:b}}}}} t)))))
