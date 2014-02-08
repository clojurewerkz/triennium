(ns clojurewerkz.triennium.trie
  (:require [clojure.core.incubator :refer [dissoc-in]]))

;;
;; API
;;

(defn make-trie
  []
  {:root {}})

(defn root-of
  [trie]
  (:root trie))

(defn insert
  ([trie segments]
     (insert trie segments {}))
  ([trie segments val]
     (let [prefix    (into [:root] segments)]
       (assoc-in trie prefix val))))


(defn delete
  [trie segments]
  {:root (dissoc-in (root-of trie) segments)})
