(ns geekteek.render-test
  "Namespace for the html rendering"
  (:use [midje.sweet]
        [geekteek.render]))

(fact
 (render-menu {"#"        "Home"
               "#about"   "about"
               "#contact" "contact"}) => [[:li [:a {:href "#"} "Home"]]
                                          [:li [:a {:href "#about"} "about"]]
                                          [:li [:a {:href "#contact"} "contact"]]]

 (render-menu :some-class {"#"        "Home"
                           "#about"   "about"
                           "#contact" "contact"}) => [:some-class [:li [:a {:href "#"} "Home"]]
                                                      [:li [:a {:href "#about"} "about"]]
                                                      [:li [:a {:href "#contact"} "contact"]]])
