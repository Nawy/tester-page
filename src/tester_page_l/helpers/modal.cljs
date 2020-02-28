(ns tester-page-l.core.helpers.modal)


(defn get-modal-type [type] 
    (case type
        "medium" "modal-lg"
        "large" "modal-xl"
        "modal-sm")) ;; default is modal-sm

(defn modal-component [modal-id title type body] 
    [:div {:class "modal fade" :id modal-id}
        [:div {:class (str "modal-dialog" " " (get-modal-type type)) :role "document"}
            [:div {:class "modal-content"}
                [:div {:class "modal-header"}
                    [:h5 {:class "modal-title"} title]
                    [:button {:class "close" :data-dismiss "modal" :aria-label "close"}
                        [:span {:aria-hidden "true"} "×"]]]
                    
                [:div {:class "modal-body"}
                    body
                [:div {:class "modal-footer"}
                    [:button {:class "btn btn-secondary" :data-dismiss "modal"} "Close"]]
                ]]]])
