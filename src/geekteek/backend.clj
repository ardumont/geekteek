(ns geekteek.backend
  "Represents the backend"
  (:require [clj-http.client :as c]))

(defn data
  "Retrieve the data from the backend"
  []
  (-> "http://code-story.net/data/codestory2013.json"
      (c/get {:as :json})
      :body))
