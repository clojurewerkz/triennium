## 1.0.0-beta2

### delete-matching

`clojurewerkz.triennium.mqtt/delete-matching` and its
`clojurewerkz.triennium.trie` counterpart are new functions
that delete values that match a predicate:

``` clojure
(require '[clojurewerkz.triennium.mqtt :as tr])

(-> (tr/make-trie)
    (tr/insert "a/topic" :a)
    (tr/insert "a/topic" :b)
    (tr/insert "a/topic" :c)
    (tr/delete-matching "a/topic" (fn [val]
                                    (#{:a :b} val))
;= {"a" {"topic" {:values #{:c}}}}
```


## 1.0.0-beta1

Initial release. A mostly correct implementation that
has one known limitation:

A/B/C/# is not detected as a match for A/B/C.

Reasonably efficient, could use some GC pressure optimisation
and optimised to not blow up the stack with a deeply nested
trie.
