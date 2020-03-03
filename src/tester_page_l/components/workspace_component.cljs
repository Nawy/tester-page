(ns tester-page-l.core.workspace)

(defn send-get-server-setting-command)

(defn command-get-server-settings []git add 
    [:div {:class "card"}
        [:div {:class "card-header"} "Server Settings"]
        [:dic {:class "card-body"}
            [:form 
                [:div {:class "form-group"}
                    [:label {:for "groupNameField"} "Group Name"]
                    [:input {:type "text" :class "form-control" :id "groupNameField" :placeholder "Group Name..."}]]
                [:button {:type "submit" :class "btn btn-primary"} "Send"]]]])

(defn workspace-component []
    [:div {:class "container-fluid"} 
        (command-get-server-settings)])

