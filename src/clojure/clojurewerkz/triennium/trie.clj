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

(ns clojurewerkz.triennium.trie
  (:refer-clojure :exclude [find])
  (:require [clojure.core.incubator :refer [dissoc-in]]
            [clojure.set :refer [union]])
  (:import clojure.lang.IFn))

(defn ^:private values-key?
  [k]
  (= :values k))

;;
;; API
;;

(def empty-trie {})

(defn make-trie
  []
  empty-trie)

(defn insert
  [trie segments val]
  (if-let [node (get-in trie segments)]
    (let [vals (conj (:values node) val)]
      (assoc-in trie segments {:values vals}))
    (assoc-in trie segments {:values #{val}})))


(defn delete
  [trie segments val]
  (if-let [node (get-in trie segments)]
    (let [vals  (:values node)
          vals' (disj vals val)]
      (if (and (empty? vals')
               (empty? (remove values-key? (keys node))))
        (dissoc-in trie segments)
        (if (seq vals')
          (assoc-in trie (conj segments :values) vals')
          (dissoc-in trie (conj segments :values)))))
    trie))

(defn delete-matching
  [trie segments ^IFn f]
  (if-let [node (get-in trie segments)]
    (let [vals  (:values node)
          vals' (set (remove f vals))]
      (if (and (empty? vals')
               (empty? (remove values-key? (keys node))))
        (dissoc-in trie segments)
        (if (seq vals')
          (assoc-in trie (conj segments :values) vals')
          (dissoc-in trie (conj segments :values)))))
    trie))

(defn find
  [trie segments]
  (if-let [n (get-in trie segments)]
    (get n :values #{})
    #{}))

(defn find-node
  [trie segments]
  (get-in trie segments))

(defn vals-of
  [node]
  (get node :values #{}))

(defn leaf?
  ([node]
     (let [ks (keys node)]
       (= '(:values) ks)))
  ([trie segments]
     (leaf? (find-node trie segments))))

(def branch? (complement leaf?))


(defn matching-vals
  ([trie segments matches-one matches-none-or-many]
     (matching-vals trie segments matches-one matches-none-or-many (dec (count segments))))
  ([trie segments matches-one matches-none-or-many remaining-depth]
     (let [node           trie
           xs             segments
           acc            #{}
           cs             (first segments)
           remaining      (rest segments)
           exact-match    (get node cs)
           any-match      (get node matches-one)
           many-match     (get node matches-none-or-many)]
       (if (zero? remaining-depth)
         (set (concat (:values exact-match)
                      (:values any-match)
                      (:values many-match)))
         (set (concat acc
                      (:values many-match)
                      (when exact-match
                        (matching-vals exact-match
                                       remaining
                                       matches-one
                                       matches-none-or-many
                                       (dec remaining-depth)))
                      (when any-match
                        (matching-vals any-match
                                       remaining
                                       matches-one
                                       matches-none-or-many
                                       (dec remaining-depth)))))))))
