(ns clojurewerkz.triennium.trie)

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
