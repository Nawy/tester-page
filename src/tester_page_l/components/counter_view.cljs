(ns tester-page-l.core.counter-view   
    (:require 
        [tester-page-l.core.counter :refer [click-count]]))

(defn counter-view-component []
    (case @click-count
        0 [:p "It's zero!"]
        1 [:p "Just one!"]
        2 [:p "Okay two..."]
        3 [:p "It's 3, do you want more?"]
        [:p "It's too much for me, It's " @click-count]))