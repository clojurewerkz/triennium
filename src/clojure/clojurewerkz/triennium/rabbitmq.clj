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

(ns clojurewerkz.triennium.rabbitmq
  (:require [clojure.string :as cs]
            [clojurewerkz.triennium.trie :as tr]))

;;
;; Implementation
;;

(def ^:const segment-separator #"\.")

;;
;; API
;;

(defn split-topic
  "Splits a topic (string) into segments, e.g.
   \"events.severity.info\" => [\"events\" \"severity\" \"info\"]"
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

(defn trie-match?
  [trie ^String topic]
  )
