(ns com.web.html.start
 (:use compojure com.db.setup clojure.contrib.server-socket))
 ;
 ; Includes For Project
 ;
 (defn main-includes[title]
	(html		
		[:head
			  [:title title]
				(include-js "/public/javascripts/jquery-1.4.2.min.js")
				(include-js "/public/javascripts/jquery.validate.js")
				(include-js "/public/javascripts/jquery-ui-1.8.2.custom.min.js")
				(include-js "/public/javascripts/script.js")		
				;
				; Style Sheets
				;
				(include-css "/public/stylesheets/jquery-ui-1.8.2.custom.css")
			    (include-css "/public/stylesheets/style.css")
			]))
 ;
 ; The Home Page Setup script
 ;
(defn home-page[title & body]
	(html 
		(main-includes title)
		[:body 	
			body
		]))
(defn create-home-body[]
	(html 	[:div#new-change
		(form-to [:post "/saveChange"]
			   [:fieldset		
				[:legend "Record Change"]
			                [:div 
						[:span 
						[:label.label {:for "enter-date"} "Date " [:em.required "(Default Today)"] ]						
						[:input#date.fields.date-pick {:type "text" :name "date"}]]]
					[:div 
						[:label.label {:for "operator"} "Operator " [:em.required "(Required)"]]
							[:select#operator.fields {:name "operator"} 
								(for [{name :name} (select-staff)] 
									[:option {:value name} name ])]]
					[:div
						[:span [:label.label {:for "change"} "Change " [:em.required "(Required)"]]
						[:input#change.fields {:type "text" :name "change"}]]]	
					[:div
						[:label.label {:for "notes"} "Notes"]
						[:textarea.fields {:name "notes"}]]
					[:div
						(submit-button {:class "ui-state-default buttons"} "Save")
					]])		
		[:input#history.ui-state-default.buttons {:type "button" :value "Show/Hide History"}]]
		[:div#data ]))

(defn format-dt [dt]
	(java.sql.Timestamp. (.getTime (.parse (java.text.SimpleDateFormat. "d-MMM-yy") dt))))

(defn save-change[params]
	(let [{dt :date name :operator change :change note :notes} params
		{staffid :id} (select-staff-name name)]
		(if (empty? dt)
			(insert-change staffid change note (now))
			(insert-change staffid change note (format-dt dt)))
	    (redirect-to "/")))
	
(defn list-changes[]
	(html 	
		[:table
			[:tr [:th "Date"] [:th "Operator"] [:th "Change"] [:th "Note"]]
				(for [{dt :created_at name :name change :change note :note} (report-changes)]
					[:tr 
						[:td dt] 
						[:td name]
						[:td change] 
						[:td (if (empty? note) 
							(str "&nbsp" note)
							note)]]
				)
				]))
;
; Define the Web App Routes
;
(defroutes changes-server
	"Create and View Snippets"
	(POST "/saveChange" (save-change params))	
	(GET "/" (home-page "Change Logger" (create-home-body)))
	(GET "/listChanges" (list-changes))
	(GET "/public/*"
    (or (serve-file (params :*)) :next))
  (ANY "*"
    (page-not-found)))	
;	
; Start the server up binding it to our routes Table
;
(defn start-server[]
	(run-server {:port 8080}
		"/*" (servlet changes-server)))
(start-server)

(create-repl-server 12345)
