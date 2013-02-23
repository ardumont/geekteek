(ns geekteek.backend
  "Represents the backend"
  (:require [clj-http.client :as c]
            [clavatar.core   :as cl]))

(defn data
  "Retrieve the data from the backend"
  []
  (->> (c/get "http://code-story.net/data/codestory2013.json" {:as :json})
       :body
       (map
        (fn [m]
          (assoc m :gravatar (-> m :EMAIL cl/gravatar))))))
