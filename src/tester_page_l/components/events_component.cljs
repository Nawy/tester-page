(ns tester-page-l.core.events
    (:require 
        [tester-page-l.core.received-events :refer [received-commands]]
        [tester-page-l.core.helpers :refer [to-json-prettify]]
        [tester-page-l.core.helpers :refer [millis-to-time]]))


(defn get-class-of-received-card [command]
    (cond 
        (true? (get command :error)) "text-white bg-danger "
        :else "text-white bg-success "))

(defn route-command [command]
    [:div {:class (str "card " (get-class-of-received-card command) "mb-2")}
        [:div {:class "card-header"} 
            [:kbd (get command :type)] " " (millis-to-time (get command :serverTime))]
        [:div {:class "card-body"}
            [:pre 
                (to-json-prettify command)]]])

(defn events-component []
    [:div
        (let [commands @received-commands]
            (if 
                (nil? commands)
                    [:p "Don't have received commands"]
                        (for [command commands] ^{:key (str "command-row-" (get command :commandId))}
                            [route-command command])))
                                [:p "Max 50 events"]])

