(ns tester-page-l.core.connection
    (:require 
        [reagent.core :as r]))

(def default-connection-list (list 
        {:name "LOCAL" :url "localhost:8080" :secure false}
        {:name "DEV" :url "backend.playtwo-dev.playtotv.com" :secure true}))

(def default-selected-connection {:name "DEV" :url "backend.playtwo-dev.playtotv.com" :secure false})

(def connection (r/atom nil))
(def connection-list (r/atom default-connection-list))

(defn clj->json
    [ds]
    (.stringify js/JSON (clj->js ds)))

(defn send-message [message]    
    (let [json (clj->json message) ws @connection]
        (.send ws json)))

(defn create-url [server] 
    (let [secure (get server :secure) url (get server :url)]
        (str (if secure "wss" "ws") "://" url "/handler")))

(defn event-connection-on-open [event]
    (send-message 
        (let [time (str (.getTime (js/Date.)))]
            {
                :commandId time
                :type "service.hello"
                :time time
            }
        )))

(defn event-connection-on-close [event]
    (js/console.log "Closed"))

(defn event-connection-on-message [event]
    (js/console.log "Message"))

(defn event-connection-on-error [event]
    (js/console.error "Socker error"))

(defn open-connection [server] 
    (let [ws (js/WebSocket. (create-url server))]
        (reset! connection ws)
        (.addEventListener ws "open" event-connection-on-open)
        (.addEventListener ws "close" event-connection-on-close)
        (.addEventListener ws "message" event-connection-on-message)
        (.addEventListener ws "error" event-connection-on-error)))

