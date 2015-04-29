(defproject lemma "0.1.0-SNAPSHOT"
  :description "A file lemmatizer which uses Standord CoreNLP"
  :url "https://github.com/BrunoBonacci/lemma"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :java-source-paths ["java"]
  :resource-paths ["resource"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:-options"]

  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [edu.stanford.nlp/stanford-corenlp "3.5.1"]
                 [edu.stanford.nlp/stanford-corenlp "3.5.1" :classifier "models"]
                 [edu.stanford.nlp/stanford-parser "3.5.1"]
                 [edu.stanford.nlp/stanford-parser "3.5.1" :classifier "models"]
                 [org.clojure/tools.cli "0.3.1"]
                 [com.taoensso/timbre "3.4.0"]
                 [me.raynes/fs "1.4.6"]]

  :main lemma.process-file

  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]
                             [lein-bin "0.3.5"]]}}

  :jvm-opts ["-server" "-Dfile.encoding=utf-8"]
  :bin {:name "lemmatizer" :bootclasspath false})
