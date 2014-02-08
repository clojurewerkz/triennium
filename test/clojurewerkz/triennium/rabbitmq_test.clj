(ns clojurewerkz.triennium.rabbitmq-test
  (:require [clojure.test :refer :all]
            [clojurewerkz.triennium.rabbitmq :as tr]))

(deftest test-split-topic
  (are [topic words] (is (= (tr/split-topic topic) words))
    "a.b.c" ["a" "b" "c"]
    "a.b.*.d" ["a" "b" "*" "d"]
    "a.b.c.#" ["a" "b" "c" "#"]))

(deftest test-insert
  (testing "case 1"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a"))]
      (is (= t2 {"a" {}}))))
  (testing "case 2"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a")
                 (tr/insert "b"))]
      (is (= t2 {"a" {} "b" {}}))))
  (testing "case 3"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a")
                 (tr/insert "b.c"))]
      (is (= t2 {"a" {} "b" {"c" {}}}))))
  (testing "case 4"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a.b")
                 (tr/insert "b.c"))]
      (is (= t2 {"a" {"b" {}} "b" {"c" {}}}))))
  (testing "case 5"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a.b")
                 (tr/insert "a.b.c"))]
      (is (= t2 {"a" {"b" {"c" {}}}}))))
  (testing "case 6"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a.b.c.d")
                 (tr/insert "a.b.c.d.*"))]
      (is (= t2 {"a" {"b" {"c" {"d" {"*" {}}}}}}))))
  (testing "case 7"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a.b.c.d.#")
                 (tr/insert "a.b.c.d.*"))]
      (is (= t2 {"a" {"b" {"c" {"d" {"*" {} "#" {}}}}}}))))
  (testing "case 8"
    (let [t  (tr/make-trie)
          t2 (-> t
                 (tr/insert "a.b.c")
                 (tr/insert "a.b.c.d.*")
                 (tr/insert "#.info")
                 (tr/insert "weather.storm.*"))]
      (is (= t2 {"weather" {"storm" {"*" {}}},
                 "#" {"info" {}},
                 "a" {"b" {"c" {"d" {"*" {}}}}}})))))

(deftest test-delete
  (testing "case 1"
    (let [t (-> (tr/make-trie)
                (tr/insert "a")
                (tr/delete "a"))]
      (is (= {} t))))
  (testing "case 2"
    (let [t (-> (tr/make-trie)
                (tr/insert "a")
                (tr/insert "b")
                (tr/insert "c")
                (tr/delete "a"))]
      (is (= {"b" {} "c" {}} t))))
  (testing "case 3"
    (let [t (-> (tr/make-trie)
                (tr/insert "a.b.c")
                (tr/insert "a.b.d")
                (tr/insert "a.c")
                (tr/delete "a.b.c"))]
      (is (= {"a" {"c" {} "b" {"d" {}}}} t))))
  (testing "case 4"
    (let [t (-> (tr/make-trie)
                (tr/insert "a.b.c")
                (tr/insert "a.b.d")
                (tr/insert "a.c")
                (tr/delete "a.b"))]
      ;; TODO: this SHOULD NOT delete children nodes.
      ;; We need to store more info in the nodes to avoid this.
      (is (= {"a" {"c" {}}} t)))))
