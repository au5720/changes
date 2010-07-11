(ns start
 (:use compojure))

 (defn start-page[]
	(html 
	
		[:head
			  [:title "ISEChanges Project"]
				(include-js "/public/javascripts/jquery-1.4.2.min.js")
				(include-js "/public/javascripts/script.js")			  
			    (include-css "/public/style.css")]	
		[:body 
			[:h2 "Welcome test"]	
		]))
(defroutes changes-server
	"Create and View Snippets"
	(GET "/" (start-page))
	(GET "/public/*"
    (or (serve-file (params :*)) :next))
  (ANY "*"
    (page-not-found)))	


	(run-server {:port 80}
	"/*" (servlet changes-server))
