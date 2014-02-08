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
  ([trie ^String topic]
     (insert trie topic {}))
  ([trie ^String topic val]
     (let [xs (split-topic topic)]
       (tr/insert trie xs val))))

(defn trie-match?
  [trie ^String topic]
  )
