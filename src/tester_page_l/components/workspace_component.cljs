(ns tester-page-l.core.workspace
    (:require [reagent.core :as r]))

(defn input-field [value id placeholder]
    [:input 
        {:type "text" 
         :class "form-control" 
         :placeholder placeholder 
         :value @value 
         :on-change #(reset! value (-> % .-target .-value))}])

(defn command-get-server-settings []
    (let [groupName (r/atom "test")]
        [:div {:class "card"}
        [:div {:class "card-header"} "Server Settings"]
        [:div {:class "card-body"}
            [:p @groupName]
            [:form 
                [:div {:class "form-group"}
                    [:label {:for "groupNameField"} "Group Name"]
                    [input-field groupName "groupNameField" "Group name..."]]
                [:button {:type "submit" :class "btn btn-primary"} "Send"]]]]))

(defn workspace-component []
    [:div {:class "container-fluid"} 
        (command-get-server-settings)])

