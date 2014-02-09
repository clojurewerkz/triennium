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
  (:require [clojure.core.incubator :refer [dissoc-in]]))

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
