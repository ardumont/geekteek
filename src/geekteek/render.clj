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
#_            [doric.core          :as doric]
            [hiccup.core         :as h]
            [hiccup.page         :as hp]))

(defn- render-left-menu
  "Render the left menu"
  [])

(defn render-main-page
  "Render the main page"
  []
  (hp/html5
   [:html {:lang "en"}
    [:head
     [:meta {:charset "utf-8"}]
     [:title "GeekTeek"]
     [:meta {:content "width=device-width, initial-scale=1.0", :name "viewport"}]
     [:meta {:content "Geetik, Meetic for Geeks!", :name "description"}]
     [:meta {:content "ardumont", :name "author"}]
     "<!-- Le styles -->"
     [:link {:media "screen", :rel "stylesheet", :href "bootstrap/css/bootstrap.min.css"}]
     [:style "\n  body {\n                padding-top: 60px; /* 60px to make the container go all\n                                 ; the way to the bottom of the topbar\n; */\n        }\n  "]
     [:link {:rel "stylesheet", :href "boostrap/css/bootstrap-responsive.css"}]
     "<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->"
     "<!-- [if lt IE 9]>\n  <script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n  <! [endif]-->"
     "<!-- Fav and touch icons -->"
     [:link {:href "boostrap/ico/apple-touch-icon-144-precomposed.png", :sizes "144x144", :rel "apple-touch-icon-precomposed"}]
     [:link {:href "boostrap/ico/apple-touch-icon-114-precomposed.png", :sizes "114x114", :rel "apple-touch-icon-precomposed"}]
     [:link {:href "boostrap/ico/apple-touch-icon-72-precomposed.png", :sizes "72x72", :rel "apple-touch-icon-precomposed"}]
     [:link {:href "boostrap/ico/apple-touch-icon-57-precomposed.png", :rel "apple-touch-icon-precomposed"}]
     [:link {:href "boostrap/ico/favicon.png", :rel "shortcut icon"}]]

    [:body
     [:div.navbar.navbar-inverse.navbar-fixed-top
      [:div.navbar-inner
       [:div.container
        [:a.btn.btn-navbar {:data-target ".nav-collapse", :data-toggle "collapse"}
         [:span.icon-bar]
         [:span.icon-bar]
         [:span.icon-bar]]
        [:a.brand {:href "#"} "GeekTeek"]
        [:div.nav-collapse.collapse
         [:ul.nav [:li.active [:a {:href "#"} "Home"]]
          [:li [:a {:href "#about"} "About"]]
          [:li [:a {:href "#contact"} "Contact"]]]]
        "<!--/.nav-collapse -->"]]]

     [:div.container-fluid
      [:div.row-fluid
       [:div.span3
        [:div.well.sidebar-nav
         [:ul.nav.nav-list
          [:li.nav-header "Menu"]
          (render-left-menu)]]
        "<!--/.well -->"]
       "<!--/span-->"
       [:div.span9
        [:form.form-inline {:method "get"}
         [:input {:type "hidden"
                  :name "pseudo"
                  :value "current-pseudo"}]
         [:input.input-large {:placeholder "http://server:port/context"
                              :type "text"
                              :value "pseudo"
                              :name "pseudo"}]
         [:button.btn {:type "submit"} "Save"]]
        "Some data to display"]
       "<!-- span9 -->"]
      "<!-- row-fluid -->"

      [:hr]
      [:footer [:p "© GeekTeek 2013"]]] "<!-- container-fluid -->"

     "<!-- Le javascript\n  ==================================================-->"
     "<!-- Placed at the end of the document so the pages load faster -->"
     [:script {:src "http://code.jquery.com/jquery-latest.js"}]
     [:script {:src "bootstrap/js/bootstrap.min.js"}]]]))
