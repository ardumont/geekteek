(ns geekteek.model
  "Represents the backend"
  (:require [clj-http.client :as c]
            [clavatar.core   :as cl]))

(defn data-people
  "Retrieve the data from the backend (json flux from code story)"
  ([]
     (->> (c/get "http://code-story.net/data/codestory2013.json" {:as :json})
          :body
          (map
           (fn [{:keys [NOM PRENOM EMAIL] :as m}]
             (-> m
                 (assoc :gravatar (cl/gravatar EMAIL))
                 (assoc :NOM (str NOM " " PRENOM))
                 (dissoc :PRENOM)
                 (dissoc :EMAIL))))))
  ([prefs]
     (->> (data-people)
          (filter (fn [{:keys [LIKE1 LIKE2 LIKE3]}]
                    (or (= prefs LIKE1)
                        (= prefs LIKE2)
                        (= prefs LIKE3)))))))

(defn data-about
  "The about data"
  []
  [:a {:href "http://adumont.fr/blog/about/"} "About me!"])

(defn data-contact
  "The contact data"
  []
  [:a {:href "http://adumont.fr/blog/"} "Contact me!"])
