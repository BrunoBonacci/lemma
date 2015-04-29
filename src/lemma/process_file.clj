(ns lemma.process-file
  (:require [lemma.core :refer :all])
  (:require [clojure.java.io :as io])
  (:require [clojure.string :refer [trim] :as s ])
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [me.raynes.fs :as fs])
  (:gen-class))


(defn process-file [source processor output]
  (println "(*) Exporting: " source " -> " output)

  (try
    ;; create parent directory if not exists
    (when (not (.exists (.getParentFile (io/file output))))
      (.mkdirs (.getParentFile (io/file output))))

    ;; finally processing file
    (->> source
         processor
         ((partial spit output)))

    (catch Exception x
      (binding [*out* *err*]
        (print (str "ERROR: processing " source ", reason: " x "\n"))))))



(defn target-file-name [root file output ext]
  (let [^String root (.getCanonicalPath (io/file root))
        ^String file (.getCanonicalPath (io/file file))
        ^String output (.getCanonicalPath (io/file output))]
    (-> file
        (.replaceFirst (str "^\\Q" root "\\E" ) output)
        (.replaceFirst (str "\\.[^.]*$") (str "." ext))
        io/file)))


(defn build-files-list [source output output-ext]
  (let [file-list      (filter #(.isFile %) (fs/find-files source #".*"))
        more-than-one (next file-list)]
    (if more-than-one
      (map (juxt identity #(target-file-name source % output output-ext)) file-list)
      [[(first file-list) (io/file output)]])))


(defn process-files [source processor output]
  (let [files (build-files-list source output "txt")]
    (doseq [[src out] files]
      (process-file src processor out))))



(def cli-options

  [["-s" "--source PATH" "A file or directory containing text file to process"
    :validate [#(.exists (io/file %)) "The file or directory must exist"]]

   ["-o" "--output PATH" "A file or directory which will contains the output files."
    :validate [#(not (.exists (io/file %))) "The file or directory must NOT exist"]]

   ["-h" "--help" "This help"]])


(defn help!
  ([cli error exit]
   (help! (update-in cli [:errors] conj error) exit))
  ([{:keys [options arguments errors summary] :as cli} exit]
    (doseq [error errors]
      (println error))
    (println summary)
    (System/exit exit)))


(defn -main [& args]
  (let [{:keys [options arguments errors summary] :as cli}
        (parse-opts args cli-options)]
    (cond
     (:help options)            (help! cli 1)
     errors                     (help! cli 2)
     (nil? (:source options))   (help! cli "MISSING: required param '-s PATH'" 3)
     (nil? (:output options))   (help! cli "MISSING: required param '-o PATH'" 4)


     :default
     (let [{:keys [source output]} options]
       (process-files source lemmatize-file output)))))



(comment
  ;; REPL interaction

  (def file "/tmp/1405051753CH4_06_02_56875006001003.txt.mallet-topic-keys.txt")

  (process-file file lemmatize-file "/tmp/1/2/3ss/test1.json")

  ;; extract json
  (println (extract-json-from-topics records))

  (export-data-file file "/tmp/1/2/3ss/test1.json")

  )
