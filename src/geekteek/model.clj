(ns geekteek.model
  "Represents the backend"
  (:require [clj-http.client :as c]
            [clavatar.core   :as cl]
            [clojure.string  :as s]))

(defn data
  "Retrieve the data from the distant land - json flux from code story."
  []
  (:body (c/get "http://code-story.net/data/codestory2013.json" {:as :json})))

(def ^{:doc "A simple decorator of the gravatar function"}
  gravatar cl/gravatar)

(defn data-people
  "Retrieve the data then add some informations then optionally filter according to the user's preferences."
  ([data]
     (->> data
          (map
           (fn [{:keys [NOM PRENOM EMAIL] :as m}]
             (-> m
                 (assoc :gravatar (gravatar EMAIL))
                 (assoc :NOM (str NOM " " PRENOM))
                 (dissoc :PRENOM)
                 (dissoc :EMAIL))))))
  ([data prefs]
     (->> data
          (filter (fn [{:keys [LIKE1 LIKE2 LIKE3]}]
                    (->> [LIKE1 LIKE2 LIKE3]
                         (map s/lower-case)
                         (some #{(s/lower-case prefs)})))))))

(defn data-about
  "The about data"
  []
  {:href "http://adumont.fr/blog/about/"
   :label "About me!"})

(defn data-contact
  "The contact data"
  []
  {:href "http://adumont.fr/blog/"
   :label"Contact me!"})
