(ns lemma.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:import  [lemmatizer StanfordLemmatizer]))


(defonce core-nlp (delay (StanfordLemmatizer.)))


(defn expand-contractions [^String text]
  (-> text
      (s/replace #"([A-Z][a-z0-9]+)'s"   "$1")
      (s/replace "let's"   "let")
      (s/replace "'s"   " is")
      (s/replace "'em"  " them")
      (s/replace "'ve"  " have")
      (s/replace "n't"  " not")
      (s/replace "'ll"  " will")
      (s/replace "'re"  " are")
      (s/replace "'m"   " am")
      (s/replace "'d"   " would")))


(defn lemmatize [^String text]
  (let [^StanfordLemmatizer nlp @core-nlp]
    (.lemmatize nlp (expand-contractions text))))


(defn lemmatize-as-text [^String text]
  (->> (lemmatize text)
       (map (partial s/join " "))
       (s/join "\n")))


(defn lemmatize-file [file]
  (->> file
       slurp
       lemmatize-as-text))


;(lemmatize "men")
