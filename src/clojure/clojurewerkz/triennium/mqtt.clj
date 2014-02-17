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

(ns clojurewerkz.triennium.mqtt
  (:refer-clojure :exclude [find])
  (:require [clojure.string :as cs]
            [clojurewerkz.triennium.trie :as tr]))

;;
;; Implementation
;;

(def ^:const segment-separator #"\/")
(def ^:const matches-one "+")
(def ^:const matches-none-or-many "#")

;;
;; API
;;

(defn split-topic
  [^String topic]
  (cs/split topic segment-separator))

(defn make-trie
  []
  (tr/make-trie))

(defn insert
  [trie ^String topic val]
  (let [xs (split-topic topic)]
    (tr/insert trie xs val)))

(defn delete
  [trie ^String topic val]
  (let [xs (split-topic topic)]
    (tr/delete trie xs val)))

(defn find
  [trie ^String topic]
  (let [xs (split-topic topic)]
    (tr/find trie xs)))

(defn find-node
  [trie ^String topic]
  (let [xs (split-topic topic)]
    (tr/find-node trie xs)))

(defn vals-of
  [trie ^String topic]
  (let [xs (split-topic topic)]
    (tr/vals-of trie xs)))

(defn leaf?
  ([node]
     (empty? (dissoc node :values)))
  ([trie ^String topic]
     (let [xs (split-topic topic)]
       (tr/leaf? trie xs))))

(defn matching-vals
  [trie ^String topic]
  (let [xs (split-topic topic)]
    (tr/matching-vals trie xs matches-one matches-none-or-many)))
