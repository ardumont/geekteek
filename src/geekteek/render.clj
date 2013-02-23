(ns geekteek.render
  "Namespace for the html rendering"
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.middleware.stacktrace :as trace]
            [ring.middleware.session :as session]
            [ring.middleware.session.cookie :as cookie]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.basic-authentication :as basic]
            [cemerick.drawbridge :as drawbridge]
            [environ.core :refer [env]]
            [doric.core          :as d]
            [hiccup.core         :as h]
            [hiccup.page         :as hp]
            [hiccup-bridge.core  :as hbc]
            [clojure.string      :as s]
            [geekteek.backend    :as b]))

(def themes
  [:amelia
   :cerulean
   :cosmo
   :cyborg
   :journal
   :readable
   :simplex
   :slate
   :spacelab
   :spruce
   :superhero
   :united])

(defn- theme-css-url
  "Return the url for the theme css"
  [{:keys [theme]}]
  (if theme
    (str "bootstrap/css/custom/" (name theme) "/bootstrap.min.css")
    "bootstrap/css/bootstrap.min.css"))

(defn- render-head
  "Render the headers"
  [app-state title]
  [:head
   [:meta {:charset "utf-8"}]
   [:title title]
   [:meta {:content "width=device-width initial-scale=1.0" :name "viewport"}]
   [:meta {:content "Geetik Meetic for Geeks!" :name "description"}]
   [:meta {:content "ardumont" :name "author"}]
   "<!-- Styles -->"
   [:link {:media "screen" :rel "stylesheet" :href (theme-css-url app-state)}]
   [:style "\n  body {\n                padding-top: 60px; /* 60px to make the container go all\n                                 ; the way to the bottom of the topbar\n; */\n        }\n  "]
   [:link {:rel "stylesheet" :href "boostrap/css/bootstrap-responsive.css"}]
   "<!-- HTML5 shim for IE6-8 support of HTML5 elements -->"
   "<!-- [if lt IE 9]>\n  <script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n  <! [endif]-->"
   "<!-- Fav and touch icons -->"
   [:link {:href "boostrap/ico/apple-touch-icon-144-precomposed.png" :sizes "144x144" :rel "apple-touch-icon-precomposed"}]
   [:link {:href "boostrap/ico/apple-touch-icon-114-precomposed.png" :sizes "114x114" :rel "apple-touch-icon-precomposed"}]
   [:link {:href "boostrap/ico/apple-touch-icon-72-precomposed.png" :sizes "72x72" :rel "apple-touch-icon-precomposed"}]
   [:link {:href "boostrap/ico/apple-touch-icon-57-precomposed.png" :rel "apple-touch-icon-precomposed"}]
   [:link {:href "boostrap/ico/favicon.png" :rel "shortcut icon"}]])

(defn render-menu
  "Given a map of links render a compojure list of menu entries."
  [class entries]
  (->> entries
       keys
       (map
        (fn [link]
          [:li [:a {:href link} (entries link)]]))
       (cons class)
       vec))

(defn render-data-as-html-table
  [data]
  (s/replace-first
   ;; data working
   (->> data
        (map
         (fn [{:keys [gravatar] :as m}]
           (assoc m :gravatar (h/html [:img {:src gravatar}]))))
        (d/table
         ^{:format d/html}
         [:gravatar :NOM :EMAIL :LIKE1 :LIKE2 :LIKE3 :HATE1 :HATE2 :VILLE]))
   ;; reworking css
   "<table>"
   "<table class=\"table table-hover table-condensed table-striped table-bordered\">"))

(defn- render-navigation-bar
  "Render the main navigation bar - at the top"
  [menu]
  [:div.navbar.navbar-inverse.navbar-fixed-top
   [:div.navbar-inner
    [:div.container
     [:a.btn.btn-navbar {:data-target ".nav-collapse" :data-toggle "collapse"}
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]]
     [:a.brand {:href "#"} "GeekTeek"]
     [:div.nav-collapse.collapse
      (render-menu :ul.nav menu)]
     "<!--/.nav-collapse -->"]]])

(defn- render-left-menu
  "Render the left menu"
  [menu]
  (render-menu :ul.nav.nav-list menu))

(defn render-main-page
  "Render the main page"
  []
  (hp/html5
   [:html {:lang "en"}
    (render-head {:theme :spacelab} "GeekTeek")

    [:body
     (render-navigation-bar {"#"        "Home"
                             "#about"   "About"
                             "#contact" "Contact"})

     [:div.container-fluid
      [:div.row-fluid

       [:div.span3
        [:div.well.sidebar-nav
         (render-left-menu {"#"        "Home"
                            "#about"   "About"
                            "#contact" "Contact"})]]

       [:div.span9
        [:form.form-inline {:method "get"}
         [:input.input-large {:placeholder "type what you like"
                              :type "text"
                              :value ""
                              :name "pref"}]
         [:button.btn {:type "submit"} "Save"]]
        (render-data-as-html-table (b/data))]]

      [:hr]
      [:footer [:p "© GeekTeek 2013"]]]

     "<!-- javascript\n  ==================================================-->"
     "<!-- Placed at the end of the document so the pages load faster -->"
     [:script {:src "http://code.jquery.com/jquery-latest.js"}]
     [:script {:src "bootstrap/js/bootstrap.min.js"}]]]))
