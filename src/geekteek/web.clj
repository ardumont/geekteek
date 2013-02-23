(ns geekteek.web
  (:require [compojure.core    :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route   :refer [resources]]
            [environ.core      :refer [env]]
            [compojure.route                      :as route]
            [clojure.java.io                      :as io]
            [ring.middleware.stacktrace           :as trace]
            [ring.middleware.session              :as session]
            [ring.middleware.session.cookie       :as cookie]
            [ring.adapter.jetty                   :as jetty]
            [ring.middleware.basic-authentication :as basic]
            [cemerick.drawbridge                  :as drawbridge]
            [geekteek.middleware                  :as m]
            [clojure.tools.trace :only [trace]    :as t]
            [geekteek.render                      :as r]
            [geekteek.backend                     :as b]))

(def ^{:private true
       :doc "The title of the site"}
  title "GeeTeek")

(def ^{:private true
       :doc "The list of links for the menu"}
  menu {"/"        "Home"
        "/about"   "About"
        "/contact" "Contact"})

(defn- response
  "A function to factorize the client response"
  [status app-data]
  {:status status
   :headers {"Content-Type" "text/html"}
   :body (r/render-main-page app-data)})

(defroutes app
  ;; repl connection
  (ANY "/repl" {:as req}
       (m/drawbridge req))

  (GET "/about" []
       (response
        200
        {:title title
         :menu  menu
         :theme :spacelab
         :data  [:a {:href "http://adumont.fr/blog/about/"} "About me!"]}))

  (GET "/contact" []
       (response
        200
        {:title title
         :menu  menu
         :theme :spacelab
         :data  [:a {:href "http://adumont.fr/blog/"} "Contact me!"]}))

  ;; Main page
  (GET "/" []
       (response
        200
        {:title title
         :menu  menu
         :form? true
         :theme :spacelab
         :data  (b/data)}))

  ;; post submission to this main page
  (POST "/" {:as req}
        (response
         201 {:title title
              :menu  menu
              :form? true
              :prefs (get-in req [:form-params "prefs"])
              :theme (get-in req [:form-params "theme"])
              :data  (b/data)}))

  ;; serve static resources
  (resources "/")

  ;; not found
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  "Main entry point"
  (let [port (Integer. (or port (env :port) 5000))
        ;; TODO: heroku config:add SESSION_SECRET=$RANDOM_16_CHARS
        store (cookie/cookie-store {:key (env :session-secret)})]
    (jetty/run-jetty (-> #'app
                         ((if (env :production) m/wrap-error-page trace/wrap-stacktrace))
                         (site {:session {:store store}})
                         m/wrap-request-logging)
                     {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
