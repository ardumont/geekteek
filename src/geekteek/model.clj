(ns geekteek.model
  "Represents the backend"
  (:require [clj-http.client :as c]
            [clavatar.core   :as cl]))

(defn data-people
  "Retrieve the data from the backend"
  []
  (->> (c/get "http://code-story.net/data/codestory2013.json" {:as :json})
       :body
       (map
        (fn [{:keys [NOM PRENOM EMAIL] :as m}]
          (-> m
              (assoc :gravatar (cl/gravatar EMAIL))
              (assoc :NOM (str NOM " " PRENOM))
              (dissoc :PRENOM)
              (dissoc :EMAIL))))))

(defn data-about
  "The about data"
  []
  [:a {:href "http://adumont.fr/blog/about/"} "About me!"])

(defn data-contact
  "The contact data"
  []
  [:a {:href "http://adumont.fr/blog/"} "Contact me!"])
