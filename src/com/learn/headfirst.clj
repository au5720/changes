(ns  com.learn.headfirst
 (:use compojure))
 
(defn lounge[]
	(html
		[:html
			[:head [:title "Head First Lounge"]]
			[:body
				[:h1 "Welcome to the Head First Lounge"]
				[:img {:src "/public/images/drinks.gif"}]
				[:p "Join us any evening for refreshing elixirs, 
					conversation and maybe a game or two of "
					[:em " Dance Dance Revolution. "]
					"Wireless access is always provided;  
					BYOWS (Bring Your Own Web Server)."]    
				[:h2 "Directions"]
				[:p	"You'll find us right in the center 
					of downtown Webville.  Come join us!"]]]))
 (defn layout [title & body]
	(html [:html [:head [:title title]
				[:style {:type "text/css"}
				"body { background-color: #d2b48c;
					margin-left: 20%;
					margin-right: 20%;
					border: 2px dotted black;
					padding: 10px 10px 10px 10px;
					font-family: sans-serif;
				}"]] [:body body]]))
			
 (defn index[]
		(layout "Starbuzz Coffee"
			[:h1 "Starbuzz Coffee Beverages"]
			
			[:h2 "House Blend, $1.49"]
			[:p "A smooth, mild blend of coffees from Mexico, Bolivia and Guatemala."]
			
			[:h2 "Mocha Cafe Latte, $2.35"]
			[:p "Espresso, steamed milk and chocolate syrup."]
			
			[:h2 "Cappuccino, $1.89"]
			[:p "A mixture of espresso, steamed milk and foam."]
			
			[:h2 "Chai Tea, $1.85"]
			[:p "A spicy drink made with black tea, spices, milk and honey."]
			))
(defn mission[]
	(layout "Starbuzz Coffee's Mission" 
		[:h1 "Starbuzz Coffee's Mission"]
		[:p "To provide all the caffeine that you need to power your life."]
		[:p "Just drink it."]))
		
(defroutes headfirst
	"Create and View Snippets"
	(GET "/chapter1" (chapter1))
	(GET "/mission" (mission))
	(GET "/index" (index))
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
