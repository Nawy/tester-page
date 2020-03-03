(ns tester-page-l.core.connection
    (:require 
        [reagent.core :as r]
        [tester-page-l.core.helpers :refer [find-connection]]
        [tester-page-l.core.helpers :refer [without-connection]]
        [tester-page-l.core.helpers :refer [sort-connections]]
        [tester-page-l.core.helpers :refer [to-json]]
        [tester-page-l.core.helpers :refer [parse-json]]
        [tester-page-l.core.helpers :refer [get-current-millis]]
        [tester-page-l.core.helpers :refer [schedule-function]]
        [tester-page-l.core.helpers :refer [make-command-request]]
        [tester-page-l.core.helpers :refer [equal-connections]]
        [tester-page-l.core.received-events :refer [add-received-command]]))

(def default-connection-list (list
        {:name "DEV" :url "backend.playtwo-dev.playtotv.com" :secure true :selected true}
        {:name "LOCAL" :url "localhost:8080" :secure false :selected false}))

(def default-selected-connection {:name "DEV" :url "backend.playtwo-dev.playtotv.com" :secure true :selected true})

;; VALUES
(def connection (r/atom nil))
(def connection-id (r/atom nil))
(def connection-list (r/atom default-connection-list))
(def connection-selected (r/atom default-selected-connection))
(def connection-status (r/atom false))

;; FUNCTIONS
 (defn update-connections-list [selected connection connection-list]
    (let 
        [
            new-connection-list (without-connection selected connection connection-list)
            removed-connection (find-connection selected connection-list)
        ]
        (sort-connections
            (if (false?
                    (equal-connections removed-connection connection))
                        (conj new-connection-list 
                            (assoc removed-connection :selected false)
                            (assoc connection :selected true))
                        (conj new-connection-list 
                            (assoc connection :selected true))))))       

(defn send-message [message]    
    (let [
        json (to-json message) 
        ws @connection
        is-ready (. @connection -readyState)]
            (if 
                (= is-ready 1)
                    (.send ws json))))

(defn create-url [server] 
    (let [secure (get server :secure) url (get server :url)]
        (str (if secure "wss" "ws") "://" url "/handler")))

(defn send-ping [] 
    (send-message (make-command-request "service.ping" nil @connection-id)))

(defn process-hello-command [command]
    (add-received-command command)
    (reset! connection-id (get command :connectionId))
    (send-ping)
    (schedule-function send-ping 3000))

(defn process-ping-command [command] 
    (let [
        command-conn-id (get command :connectionId)]
            (if 
                (not 
                    (= command-conn-id @connection-id))
                        (reset! connection-id command-conn-id))
            (js/console.log "Got ping with " (get command :connectionId))
            (reset! connection-status true)))

(defn process-error-command [command] 
    (add-received-command command))

(defn process-any-command [command] 
    (add-received-command command))

(defn route-command [command]
    (if (true? (get command :error))
        (process-error-command command)
        (let [type (get command :type)]
            (case type
                "service.hello" (process-hello-command command)
                "service.ping" (process-ping-command command)
                (process-any-command command)))))

(defn event-connection-on-open [server]
    (fn [event] 
        (reset! connection-list 
            (update-connections-list @connection-selected server @connection-list))
        (reset! connection-selected server)
        (send-message (make-command-request "service.hello" nil nil))
    )
)

(defn event-connection-on-close [event]
    (reset! connection-status false)
    (js/console.log "Closed"))

(defn event-connection-on-message [event]
    (let [message (parse-json (js->clj (. event -data)))]
        (route-command message)
    )
)

(defn event-connection-on-error [event]
    (js/console.error "Socker error"))


(defn open-connection [server] 
    (let [ws (js/WebSocket. (create-url server))]
        (reset! connection ws)
        (reset! connection-status false)
        (.addEventListener ws "open" (event-connection-on-open server))
        (.addEventListener ws "close" event-connection-on-close)
        (.addEventListener ws "message" event-connection-on-message)
        (.addEventListener ws "error" event-connection-on-error)))