(ns clojurewerkz.triennium.mqtt
  (:require [clojure.string :as cs]))

;;
;; Implementation
;;

(def ^:const segment-separator #"\/")

;;
;; API
;;

(defn split-topic
  [^String topic]
  (cs/split topic segment-separator))

(defn matches?
  ([^String topic segments]
     (let [xs (split-topic topic)]
       )))
