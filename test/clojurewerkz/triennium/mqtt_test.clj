(ns clojurewerkz.triennium.mqtt-test
  (:require [clojure.test :refer :all]
            [clojurewerkz.triennium.mqtt :as tr]))

(deftest test-split-topic
  (are [topic words] (is (= (tr/split-topic topic) words))
    "a/b/c" ["a" "b" "c"]
    "a/b/+/d" ["a" "b" "+" "d"]
    "a/b/c/#" ["a" "b" "c" "#"]))

(deftest test-insert
  (testing "case 1"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a" :a))]
      (is (= t2 {"a" {:values #{:a}}}))))
  (testing "case 2"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a" :a)
                 (tr/insert "b" :b))]
      (is (= t2 {"a" {:values #{:a}} "b" {:values #{:b}}}))))
  (testing "case 3"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a" :a)
                 (tr/insert "b/c" :c))]
      (is (= t2 {"a" {:values #{:a}} "b" {"c" {:values #{:c}}}}))))
  (testing "case 4"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a/b" :b)
                 (tr/insert "b/c" :c))]
      (is (= t2 {"a" {"b" {:values #{:b}}} "b" {"c" {:values #{:c}}}}))))
  (testing "case 5"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a/b" :b)
                 (tr/insert "a/b/c" :c))]
      (is (= t2 {"a" {"b" {:values #{:b} "c" {:values #{:c}}}}}))))
  (testing "case 6"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a/b/c/d" :a)
                 (tr/insert "a/b/c/d/+" :b))]
      (is (= t2 {"a" {"b" {"c" {"d" {:values #{:a} "+" {:values #{:b}}}}}}}))))
  (testing "case 7"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a/b/c/d/#" :one)
                 (tr/insert "a/b/c/d/+" :two))]
      (is (= t2 {"a" {"b" {"c" {"d" {"+" {:values #{:two}} "#" {:values #{:one}}}}}}}))))
  (testing "case 8"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a/b/c" :a)
                 (tr/insert "a/b/c/d/+" :b)
                 (tr/insert "info/#" :c)
                 (tr/insert "weather/storm/+" :d))]
      (is (= t2 {"weather" {"storm" {"+" {:values #{:d}}}}
                 "info" {"#" {:values #{:c}}},
                 "a" {"b" {"c" {:values #{:a} "d" {"+" {:values #{:b}}}}}}})))))

(deftest test-delete
  (testing "case 1"
    (let [t (-> (tr/make-trie)
                (tr/insert "a" :a)
                (tr/delete "a" :a))]
      (is (= {} t))))
  (testing "case 2"
    (let [t (-> (tr/make-trie)
                (tr/insert "a" :a)
                (tr/insert "b" :b)
                (tr/insert "c" :c)
                (tr/delete "a" :a))]
      (is (= {"b" {:values #{:b}} "c" {:values #{:c}}} t))))
  (testing "case 3"
    (let [t (-> (tr/make-trie)
                (tr/insert "a/b/c" :a)
                (tr/insert "a/b/d" :b)
                (tr/insert "a/c" :c)
                (tr/delete "a/b/c" :a))]
      (is (= {"a" {"c" {:values #{:c}} "b" {"d" {:values #{:b}}}}} t))))
  (testing "case 4"
    (let [t (-> (tr/make-trie)
                (tr/insert "a/b/c" :a)
                (tr/insert "a/b/d" :b)
                (tr/insert "a/c" :c)
                (tr/delete "a/b" :d))]
      (is (= {"a" {"c" {:values #{:c}}
                   "b" {"d" {:values #{:b}}
                   "c" {:values #{:a}}}}} t)))))
