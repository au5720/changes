(ns  com.learn.ch2
 (:use compojure))
 
(defn layout [title & body]
	(html [:html 
		[:head [:title title]] 
		[:body body]]))

(defn lounge[]
	(layout "Head First Lounge"
		[:h1 "Welcome to the Head First Lounge"]
		[:img {:src "/public/images/drinks.gif"}]
    
		[:p "Join us any evening for refreshing elixirs,"
		"conversation and maybe a game or two of "
		[:em "Dance Dance Revolution"]  
		"Wireless access is always provided;  "
		"BYOWS (Bring Your Own Web Server)."]
    
    [:h2 "Directions"]
    [:p
      "You'll find us right in the center "
      "of downtown Webville.  Come join us!"
    ]	
	))
(comment		
(defn directions[]
	(layout "Head First Lounge Directions
	
	))
 	
(defn elixir[]
	(layout "Head First Lounge Elixirs"
	
	)))
(defroutes headfirst
	"Chapter 2"
	(GET "/elixir" (elixir))
	(GET "/directions" (directions))
	(GET "/" (lounge))
	(GET "/public/*"
    (or (serve-file (params :*)) :next))
  (ANY "*"
    (page-not-found)))	
;	
; Start the server up binding it to our routes Table
;
(defn start-server[]
	(run-server {:port 8080}
		"/*" (servlet headfirst)))
