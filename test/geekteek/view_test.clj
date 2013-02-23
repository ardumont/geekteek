(ns geekteek.view_test
  "Namespace for the html rendering"
  (:use [midje.sweet]
        [geekteek.render]))

(fact
 (render-menu :some-class {"#"        "Home"
                           "#about"   "about"
                           "#contact" "contact"}) => [:some-class [:li [:a {:href "#"} "Home"]]
                                                      [:li [:a {:href "#about"} "about"]]
                                                      [:li [:a {:href "#contact"} "contact"]]])
