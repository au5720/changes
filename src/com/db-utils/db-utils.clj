(ns com.db-utils.db-utils)
(use 'clojure.contrib.sql)
(def db-path "C:/projects/ISEChanges/bin/MyDbTest")

(def db {:classname "org.apache.derby.jdbc.EmbeddedDriver"
           :subprotocol "derby"
           :subname db-path
           :create true})

(with-connection db
	(with-query-results rs ["select * from derbyDB"]
		(dorun (map #(println (:num %)) rs))))
