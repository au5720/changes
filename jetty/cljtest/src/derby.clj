(ns derby)
(import '(java.sql DriverManager Connection PreparedStatement ResultSet)) 
(class org.apache.derby.jdbc.ClientDriver) 
(def conn (. DriverManager (getConnection "jdbc:derby://localhost:1527//home/jennifer/Desktop/projects/ISEChanges/database/TestDB")))

(def rs (.. conn (prepareStatement  "select * from test") 
	(executeQuery))) 
(def rset (resultset-seq rs)) 
(count rset) 
(map #(println (:name %)) rset)


