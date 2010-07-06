(ns com.repl
(:require clojure.main)
   (:import (java.io InputStreamReader PrintWriter)
                  (java.net ServerSocket Socket)
                  (clojure.lang LineNumberingPushbackReader)))

(defn do-on-thread
   "Create a new thread and run function f on it. Returns the thread object that
    was created."
   [f]
   (let [thread (new Thread f)]
      (.start thread)
      thread))

(defn socket-repl
   "Start a new REPL that is connected to the input/output streams of
    socket."
   [socket]
   (let [socket-in (new LineNumberingPushbackReader
                                 (new InputStreamReader
                                    (.getInputStream socket)))
           socket-out (new PrintWriter
                                   (.getOutputStream socket) true)]
      (binding [*in* socket-in
                       *out* socket-out
                       *err* socket-out]
         (clojure.main/repl))))

(defn start-repl-server
   "Creates a new thread and starts a REPL server on listening on port. Returns
    the server socket that was just created."
   [port]
   (let [server-socket (new ServerSocket port 0)]
      (do-on-thread #(while true (socket-repl (.accept server-socket))))
      server-socket))
