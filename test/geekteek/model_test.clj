(ns geekteek.model_test
  "Namespace for the data building"
  (:use [midje.sweet]
        [geekteek.model]))

(fact :data-people-without-filter
  (let [data [{:NOM "Antoine" :LIKE2 "JavaScript" :HATE1 "Passwords" :LIKE1 "HTML5" :VILLE "Paris" :PRENOM "Philippe" :HATE2 "Configs xml" :EMAIL "phil.antoine@gmail.com" :LIKE3 "Barcamps"}
              {:NOM "Ardhuin" :LIKE2 "java" :HATE1 "IE" :LIKE1 "Coder" :VILLE "Nancy" :PRENOM "Alexandre" :HATE2 "le code pourri" :EMAIL "alexandre.ardhuin@gmail.com" :LIKE3 "web"}]]
    (data-people data) => [{:gravatar :g1 :NOM "Antoine Philippe" :LIKE2 "JavaScript" :HATE1 "Passwords" :LIKE1 "HTML5" :VILLE "Paris" :HATE2 "Configs xml" :LIKE3 "Barcamps"}
                           {:gravatar :g2 :NOM "Ardhuin Alexandre" :LIKE2 "java" :HATE1 "IE" :LIKE1 "Coder" :VILLE "Nancy" :HATE2 "le code pourri" :LIKE3 "web"}]
    (provided
      (gravatar "phil.antoine@gmail.com") => :g1
      (gravatar "alexandre.ardhuin@gmail.com") => :g2)))

(fact :data-people-with-filter
  (let [data [{:NOM "Antoine" :LIKE2 "JavaScript" :HATE1 "Passwords" :LIKE1 "HTML5" :VILLE "Paris" :PRENOM "Philippe" :HATE2 "Configs xml" :EMAIL "phil.antoine@gmail.com" :LIKE3 "Barcamps"}
              {:NOM "Ardhuin" :LIKE2 "java" :HATE1 "IE" :LIKE1 "Coder" :VILLE "Nancy" :PRENOM "Alexandre" :HATE2 "le code pourri" :EMAIL "alexandre.ardhuin@gmail.com" :LIKE3 "web"}]]
    (data-people data "java") => [{:gravatar :g2 :NOM "Ardhuin Alexandre" :LIKE2 "java" :HATE1 "IE" :LIKE1 "Coder" :VILLE "Nancy" :HATE2 "le code pourri" :LIKE3 "web"}]
    (provided
      (gravatar "alexandre.ardhuin@gmail.com") => :g2)))

(fact :data-about
  (data-about) => {:href "http://adumont.fr/blog/about/"
                   :label "About me!"})

(fact :data-contact
  (data-contact) => {:href "http://adumont.fr/blog/"
                     :label "Contact me!"})
