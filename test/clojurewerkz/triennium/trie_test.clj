;; Copyright (c) 2014 Michael S. Klishin, Alex Petrov, and the ClojureWerkz Team
;;
;; This file is provided to you under the Apache License, Version 2.0 (the
;; "License"); you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;; http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
;; WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
;; License for the specific language governing permissions and limitations under
;; the License.

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

(deftest test-matching-vals
  (testing "case 1"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a" "1"] :a1)
                (tr/insert ["a" "2"] :a2)
                (tr/insert ["a" "3"] :a3)
                (tr/insert ["a" "b" "1"] :ab1))
          xs ["a" "2"]]
      (is (= #{:a2} (tr/matching-vals t xs "+" "#")))))
  (testing "case 2"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a" "1"] :a1)
                (tr/insert ["a" "+"] :a+)
                (tr/insert ["a" "#"] :a#)
                (tr/insert ["a" "b" "1"] :ab1))
          xs ["a" "1"]]
      (is (= #{:a1 :a+ :a#} (tr/matching-vals t xs "+" "#")))))
  (testing "case 3"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a" "1"] :a1)
                (tr/insert ["a" "+"] :a+)
                (tr/insert ["a" "#"] :a#)
                (tr/insert ["a" "b" "1"] :ab1))
          xs ["a" "2"]]
      (is (= #{:a+ :a#} (tr/matching-vals t xs "+" "#")))))
  (testing "case 4"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a" "+" "1"] :a+1)
                (tr/insert ["a" "+" "2"] :a+2)
                (tr/insert ["a" "+" "#"] :a+#)
                (tr/insert ["a" "b" "1"] :ab1))
          xs ["a" "b" "1"]]
      (is (= #{:a+1 :a+# :ab1} (tr/matching-vals t xs "+" "#")))))
  (testing "case 5"
    (let [t (-> (tr/make-trie)
                (tr/insert ["a" "+" "1" "+" "2"] :a+1+2)
                (tr/insert ["a" "+" "2"] :a+2)
                (tr/insert ["a" "b" "#"] :ab#)
                (tr/insert ["a" "b" "1"] :ab1))
          xs ["a" "b" "1"]]
      (is (= #{:ab# :ab1} (tr/matching-vals t xs "+" "#")))))
  (testing "case 6"
    (let [t (-> (tr/make-trie)
                (tr/insert ["r" "a"] :ra)
                (tr/insert ["r" "a" "+"] :ra+)
                (tr/insert ["r" "+" "d"] :r+d)
                (tr/insert ["r" "c" "#"] :rc#))
          xs ["r" "a" "c"]]
      (is (= #{:ra+} (tr/matching-vals t xs "+" "#")))))
  (testing "case 7"
    (let [t (-> (tr/make-trie)
                (tr/insert ["r" "a"] :ra)
                (tr/insert ["r" "a" "+"] :ra+)
                (tr/insert ["r" "+" "d"] :r+d)
                (tr/insert ["r" "b" "#"] :rb#))
          xs ["r" "b" "d"]]
      (is (= #{:r+d :rb#} (tr/matching-vals t xs "+" "#")))))
  (testing "case 8"
    (let [t (-> (tr/make-trie)
                (tr/insert ["r" "a"] :ra)
                (tr/insert ["r" "a" "+"] :ra+)
                (tr/insert ["r" "+" "d"] :r+d)
                (tr/insert ["r" "b" "#"] :rb#))
          xs ["r" "x" "b"]]
      (is (= #{} (tr/matching-vals t xs "+" "#")))))
  (testing "case 9"
    (let [t (-> (tr/make-trie)
                (tr/insert ["r" "a"] :ra)
                (tr/insert ["r" "a" "+"] :ra+)
                (tr/insert ["r" "+" "d"] :r+d)
                (tr/insert ["r" "b" "#"] :rb#))
          xs ["r" "x" "b"]]
      (is (= #{} (tr/matching-vals t xs "+" "#")))))
  (testing "case 10"
    (let [t (-> (tr/make-trie)
                (tr/insert ["r" "a"] :ra)
                (tr/insert ["r" "a" "+" "x" "z" "+"] :ra+xz+)
                (tr/insert ["r" "a" "+" "+" "+" "#"] :ra+++#)
                (tr/insert ["r" "+" "#"] :r+#))
          xs ["r" "a" "a" "x" "z" "z"]]
      (is (= #{:ra+xz+ :ra+++# :r+#} (tr/matching-vals t xs "+" "#")))))
  (testing "case 11"
    (let [t (-> (tr/make-trie)
                (tr/insert ["r" "a"] :ra)
                (tr/insert ["r" "a" "+" "x" "z" "+"] :ra+xz+)
                (tr/insert ["r" "a" "+" "+" "+"] :ra+++)
                (tr/insert ["r" "+" "#"] :r+#))
          xs ["r" "a" "a" "x" "z"]]
      (is (= #{:ra+++ :r+#} (tr/matching-vals t xs "+" "#")))))
  (testing "case 12"
    (let [t (-> (tr/make-trie)
                (tr/insert ["r" "a"] :ra)
                (tr/insert ["r" "a" "b" "c" "d" "e"] :rabcde)
                (tr/insert ["r" "a" "+" "1" "+"] :ra+++)
                (tr/insert ["r" "z" "#"] :rz#))
          xs ["r" "a" "b" "c" "d" "e"]]
      (is (= #{:rabcde} (tr/matching-vals t xs "+" "#"))))))
