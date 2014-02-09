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

;;
;; API
;;

(def empty-trie {})

(defn make-trie
  []
  empty-trie)

(defn insert
  ([trie segments]
     (insert trie segments {}))
  ([trie segments val]
     (assoc-in trie segments val)))


(defn delete
  [trie segments]
  (dissoc-in trie segments))
