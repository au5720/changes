(ns HelloServlet
  (:gen-class :extends javax.servlet.http.HttpServlet :main false)
  (:import javax.servlet.http.HttpServletResponse))

(defn -doGet [this request response]
  (let [out (.getWriter response)]
    (.println out "Hello, world!"))
  (doto response
    (.setContentType "text/plain")
    (.setStatus HttpServletResponse/SC_OK)))


(defn -doPost [this request response]
  (let [out (.getWriter response)
	field (.getParameter request "field")
	ret (str "<html><body>You entered " field " into box.</body></html>")]
    (.println out ret)))
;  (doto response
;    (.setContentType "text/html")
;    (.setStatus HttpServletResponse/SC_OK))))

