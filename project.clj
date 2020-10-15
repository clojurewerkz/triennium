(defproject clojurewerkz/triennium "1.0.0-beta3-SNAPSHOT"
  :description "Small library that strives to provide efficient
                topic routing implementations for MQTT topic schemes"
  :dependencies [[org.clojure/clojure        "1.6.0"]
                 [org.clojure/core.incubator "0.1.3"]]
  :profiles {:1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0"]]}
             :master {:dependencies [[org.clojure/clojure "1.8.0-master-SNAPSHOT"]]}
             :dev {:resource-paths ["test/resources"]
                   :plugins [[codox "0.8.10"]]
                   :codox {:sources ["src/clojure"]
                           :output-dir "doc/api"}
                   :dependencies [[org.clojure/test.check "1.1.0"]
                                  [criterium "0.4.6"]]}
             :perf {:jvm-opts ^:replace ["-server"]
                             "-Xmx4096m"
                             "-Dclojure.compiler.direct-linking=true"
                    :test-paths ["perf-test/clj"]}}
  :aliases {"all" ["with-profile" "dev:dev,1.8:dev,1.9:dev,master"]
            "perf" ["with-profile" "default,dev,perf"]}
  :repositories {"sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}}
  :javac-options      ["-target" "1.6" "-source" "1.6"]
  :jvm-opts           ["-Dfile.encoding=utf-8"]
  :source-paths       ["src/clojure"]
  :java-source-paths  ["src/java"])
