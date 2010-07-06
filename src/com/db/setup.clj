(ns com.db.setup)
(use 'clojure.contrib.sql)

(defn create-name-table []
  (create-table :staff
    [:id :int "IDENTITY" "PRIMARY KEY"]
    [:name :varchar "NOT NULL"]))
	
(defn create-changes-table []
  (create-table :changes
    [:id :int "IDENTITY" "PRIMARY KEY"]
	[:created_at :datetime  "NOT NULL"]
	[:staffid :int  "NOT NULL"]
	[:change :varchar "NOT NULL"]
    [:note :varchar]))
	
(defn create-tables[]
	(do
		(create-name-table)
		(create-changes-table)))
	
; replace "snippet-db" with a full path!
(def db {:classname "org.hsqldb.jdbcDriver"
         :subprotocol "hsqldb"
         :subname "file:changes-db"})

(defn drop-changes-tables[]
  (try
	(do
		(drop-table :staff)
		(drop-table :changes))
   (catch Exception e)))
	
(defn now [] (java.sql.Timestamp. (.getTime (java.util.Date.)))) 

(defn insert-staff[]
    (seq 
     (insert-values :staff
	  [:name]
          ["lisa"]		     
	  ["marge"]		     
	  ["bart"]		     
	  ["homer"])))

(defn insert-changes[]
   (seq 
     (insert-values :changes
      [:created_at :staffid :change :note]		     
      [(now) 1 "Some Changes to SErvers" ""]
	  [(now) 2 "windows Reboots"]
	  [(now) 2 "More windows Reboots"]
	  [(now) 2 "And another windows Reboots"]
	  [(now) 0 "Oracle DB backup" "Will need a look at next maintainence W/E"])))
	  
(defn sample-changes[]
  (with-connection db
     (drop-changes-tables)
     (create-tables)
     (insert-staff)
	 (insert-changes)))

(defn reset-changes []
  (with-connection db
     (drop-changes-tables)
     (create-tables)
     (insert-staff)))

(defn ensure-snippets-table-exists []
  (try
   (with-connection db (create-tables))
   (catch Exception _)))
   
(defn print-changes []
 (do
	(with-query-results res ["select * from staff"]
		(println res))
	(with-query-results res ["select * from changes"]
		(println res))
	))

(defmulti coerce (fn [dest-class src-inst] [dest-class (class src-inst)]))
(defmethod coerce [Integer String] [_ inst] (Integer/parseInt inst))
(defmethod coerce :default [dest-cls obj] (cast dest-cls obj))

(defn select-staff []
  (with-connection db
    (with-query-results res ["select * from staff"] (doall res))))

(defn select-changes []
  (with-connection db
    (with-query-results res ["select * from changes"] (doall res))))

(defn report-changes[]
  (with-connection db
    (with-query-results res 
		[(str "select changes.id,changes.created_at,staff.name,changes.change,changes.note from "
			" changes,staff where changes.staffid=staff.id order by changes.id desc")
			] (doall res))))

(defn sql-query [q]
  (with-query-results res q (doall res)))

 (defn select-staff-id [id]
  (with-connection db
    (first (sql-query ["select * from staff where id = ?" (coerce Integer id)]))))
	
(defn select-staff-name [name]
  (with-connection db
    (first (sql-query ["select * from staff where name = ?" name]))))

(defn select-change-id [id]
  (with-connection db
    (first (sql-query ["select * from changes where id = ?" (coerce Integer id)]))))

(defn select-changes-by-staff-id[id]
  (with-connection db
    (sql-query ["select * from changes where staffid = ?" (coerce Integer id)])))

(defn last-created-id[]
  (first (vals (first (sql-query ["CALL IDENTITY()"])))))

(defn insert-change 
([staffid change note] (insert-change staffid change note (now)))
([staffid change note create_dt]
  (with-connection db
    (transaction
     (insert-values :changes
       [:created_at :staffid :change :note]
       [create_dt staffid change note])
     (last-created-id)))))
