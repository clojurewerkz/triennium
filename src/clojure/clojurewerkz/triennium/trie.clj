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
