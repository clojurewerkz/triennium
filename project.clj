(defproject clojurewerkz/triennium "1.0.0-beta3-SNAPSHOT"
  :description "Small library that strives to provide efficient
                topic routing implementations for MQTT topic schemes"
  :dependencies [[org.clojure/clojure        "1.10.1"]
                 [org.clojure/core.incubator "0.1.4"]]
  :profiles {:1.8 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :master {:dependencies [[org.clojure/clojure "1.10.1"]]}
             :dev {:resource-paths ["test/resources"]
                   :plugins [[codox "0.10.7"]]
                   :codox {:sources ["src/clojure"]
                           :output-dir "doc/api"}
                   :dependencies [[org.clojure/test.check "1.1.0"]]}}
  :aliases {"all" ["with-profile" "dev:dev,1.8:dev,1.9:dev,master"]}
  :repositories {"sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}}
  :javac-options      ["-target" "1.8" "-source" "1.8"]
  :jvm-opts           ["-Dfile.encoding=utf-8"]
  :source-paths       ["src/clojure"]
  :java-source-paths  ["src/java"])
