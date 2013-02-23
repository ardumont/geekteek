(ns geekteek.web
  (:require [compojure.core    :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [environ.core      :refer [env]]
            [compojure.route                      :as route]
            [clojure.java.io                      :as io]
            [ring.middleware.stacktrace           :as trace]
            [ring.middleware.session              :as session]
            [ring.middleware.session.cookie       :as cookie]
            [ring.adapter.jetty                   :as jetty]
            [ring.middleware.basic-authentication :as basic]
            [cemerick.drawbridge                  :as drawbridge]
            [geekteek.middleware                  :as m]))

(defroutes app
  (ANY "/repl" {:as req}
       (drawbridge req))
  (GET "/" []
       {:status 200
        :headers {"Content-Type" "text/html"}
        :body "Hello world!"})
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))
        ;; TODO: heroku config:add SESSION_SECRET=$RANDOM_16_CHARS
        store (cookie/cookie-store {:key (env :session-secret)})]
    (jetty/run-jetty (-> #'app
                         ((if (env :production) m/wrap-error-page trace/wrap-stacktrace))
                         (site {:session {:store store}})
                         m/wrap-request-logging)
                     {:port port :join? false})))

;; For interactive development:
 (.stop server)
 (def server (-main))
