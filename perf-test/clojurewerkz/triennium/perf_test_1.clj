(require '[clojurewerkz.triennium.mqtt :as tr])
(require '[criterium.core :as cc])

(def trie
  (-> (tr/make-trie)
      (tr/insert "a/+/1" :a+1)
      (tr/insert "a/+/2" :a+2)
      ;(tr/insert "a/+/#" :a+#)
      (tr/insert "a/b/1" :ab1)))

(cc/quick-bench
  (dotimes [_ 1000]
    (tr/matching-vals trie "a/x/1")))

(cc/quick-bench
  (dotimes [_ 1000]
    (tr/matching-vals trie "a/thisisatest/more/data")))
