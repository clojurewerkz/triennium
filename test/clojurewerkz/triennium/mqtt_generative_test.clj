(ns clojurewerkz.triennium.mqtt-generative-test
  (:require [clojure.test :refer :all]
            [clojurewerkz.triennium.mqtt :as tr]
            [clojure.string :as cs]
            [clojure.test.check :as sc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as ct :refer (defspec)]))

(defspec single-segment-matching-case1 10000
  (let [v :metrics+
        t (-> (tr/make-trie)
              (tr/insert "metrics/+" v))]
    (prop/for-all [s (gen/not-empty gen/string-alpha-numeric)]
                  (= #{v}
                     (tr/matching-vals t (format "metrics/%s" s))))))

(defspec exact-segment-matching-case1 10000
  (prop/for-all [i (gen/not-empty gen/string-alpha-numeric)
                 n gen/int]
                (let [s (format "metrics/%s" i)
                      t (-> (tr/make-trie)
                            (tr/insert "metrics/gc/young-gen" 99)
                            (tr/insert s n))]
                  (= #{n}
                     (tr/matching-vals t s)))))

(defspec exact-segment-matching-case2 500
  (prop/for-all [xs (gen/not-empty (gen/vector (gen/such-that (fn [^String s]
                                                                (> (.length s) 3))
                                                              gen/string-alpha-numeric)))
                 n  gen/int]
                (let [s (cs/join "/" xs)
                      t (-> (tr/make-trie)
                            (tr/insert "a/b/c/d" 99)
                            (tr/insert s n))]
                  (= #{n}
                     (tr/matching-vals t s)))))

(defspec many-segment-matching-case1 10000
  (prop/for-all [i (gen/not-empty gen/string-alpha-numeric)
                 n gen/int]
                (let [s (format "metrics/hardware/%s" i)
                      t (-> (tr/make-trie)
                            (tr/insert "metrics/#" n))]
                  (= #{n}
                     (tr/matching-vals t s)))))

(defspec no-matching-case1 10000
  (prop/for-all [i (gen/not-empty gen/string-alpha-numeric)
                 n gen/int]
                (let [s (format "metrics/hardware/%s" i)
                      t (-> (tr/make-trie)
                            (tr/insert "metrics/os/file-handles/open" n))]
                  (empty? (tr/matching-vals t s)))))

(defspec mixed-segment-matching-case1 10000
  (prop/for-all [i  (gen/not-empty gen/string-alpha-numeric)]
                (let [v1 :v1
                      v2 :v2
                      v3 :v3
                      s (format "root/a/%s" i)
                      t  (-> (tr/make-trie)
                             (tr/insert "root/a/+" v1)
                             (tr/insert "root/a/#" v2)
                             (tr/insert (format "root/c/%s" i) v3))]
                  (= #{v1 v2}
                     (tr/matching-vals t s)))))
