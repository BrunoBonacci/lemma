(ns lemma.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:import  [lemmatizer StanfordLemmatizer]))


(defonce core-nlp (delay (StanfordLemmatizer.)))


(defn lemmatize [^String text]
  (let [^StanfordLemmatizer nlp @core-nlp]
    (.lemmatize nlp text)))


(defn lemmatize-as-text [^String text]
  (->> (lemmatize text)
       (map (partial s/join " "))
       (s/join "\n")))


(defn lemmatize-file [file]
  (->> file
       slurp
       lemmatize-as-text))



;;(lemmatize "That which doesn't kill us makes us stronger. Friedrich Nietzsche")
;;=> [["that" "which" "do" "not" "kill" "we" "make" "we" "stronger" "."]
;;    ["Friedrich" "Nietzsche"]]

;;(lemmatize-as-text "That which doesn't kill us makes us stronger. Friedrich Nietzsche")
;;=> "that which do not kill we make we stronger .\nFriedrich Nietzsche"
