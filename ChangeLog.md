## 1.0.0-beta1

Initial release. A mostly correct implementation that
has one known limitation:

A/B/C/# is not detected as a match for A/B/C.

Reasonably efficient, could use some GC pressure optimisation
and optimised to not blow up the stack with a deeply nested
trie.
