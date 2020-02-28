(ns tester-page-l.core.counter    
    (:require 
        [reagent.core :as r]))

(def click-count (r/atom 0))

(defn counter-component [] 
    [:p 
        "Increase counter " 
        @click-count 
        " " 
        [:button 
            {:class "btn btn-primary" :type "button" :on-click #(swap! click-count inc)} 
            "Just count!"]
    ])