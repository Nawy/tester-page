(ns tester-page-l.core.received-events
    (:require 
        [reagent.core :as r]
        [tester-page-l.core.helpers :refer [sort-events]]))

;; VALUES
(def received-commands (r/atom nil))

(defn add-received-command [command] 
    (sort-events 
        (if 
            (nil? @received-commands)
                (reset! received-commands (list command))
                (reset! received-commands (conj @received-commands command)))))