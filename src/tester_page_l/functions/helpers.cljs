(ns tester-page-l.core.helpers)

;;
;; Find connection
;;
(defn find-connection [find-conn connections] 
    (let [
            founded (filter (fn [conn] (= (get conn :url) (get find-conn :url))) connections)
        ]
        (if (> (count founded) 0)
            (nth founded 0) nil)))

;;
;; FILTER CONNECTIONS WITHOUT conn1 and conn2
;;
(defn without-connection [conn1 conn2 connections] 
    (filter 
        (fn [conn] 
            (not 
                (let 
                    [url1 (get conn :url)]
                    (or 
                        (= 
                            url1
                            (get conn1 :url))
                        (= 
                            url1
                            (get conn2 :url)))))) 
                                connections))

;;
;; SORT CONNECTIONS
;;
(defn sort-connections [connections] 
    (sort 
        #(compare (get %1 :name) (get %2 :name))
        connections))

;;
;; SORT EVENTS
;;
(defn sort-events [connections] 
    (sort-by (get % :serverTime) > connections))

;;
;; COMPARE CONNECTION
;;
(defn equal-connections [conn1 conn2]
    (= (get conn1 :url) (get conn2 :url)))

(defn millis-to-time [millis]
    (let 
        [date (js/Date. millis)]
        (str 
            (.getFullYear date) "." 
            (get-number-with-zero (+ (.getMonth date) 1)) "."
            (get-number-with-zero (.getDate date)) "-"
            (get-number-with-zero (.getHours date)) ":"
            (get-number-with-zero (.getMinutes date)) ":"
            (get-number-with-zero (.getSeconds date)) "."
            (.getMilliseconds date))))

(defn get-number-with-zero [number]
    (if (< number 10)
        (str "0" number)
        (str number)))

;;
;; TO JSON
;;
(defn to-json
    [ds]
    (.stringify js/JSON (clj->js ds)))

(defn to-json-prettify
    [ds]
    (.stringify js/JSON (clj->js ds) nil 2))

;;
;; PARSE JSON
;;
(defn parse-json
    [value]
    (js->clj (.parse js/JSON value) :keywordize-keys true))

;;
;; GET CURRENT MILLIS
;;
(defn get-current-millis [] (.getTime (js/Date.)))

;;
;; SCHEDULE FUNCTION
;;
(defn schedule-function [function time] 
    (js/setInterval function time))

;;
;; MAKE COMMAND REQUEST
;;
(defn make-command-request [type payload connection-id] 
    (let 
        [timestamp (get-current-millis)]
        {
            :commandId (if (nil? connection-id) (str timestamp) connection-id)
            :type type
            :time timestamp
            :payload payload
        }
    ))