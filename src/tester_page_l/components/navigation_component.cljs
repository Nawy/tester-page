(ns tester-page-l.core.navigation
    (:require 
        [tester-page-l.core.counter :refer [counter-component]]
        [tester-page-l.core.helpers.modal :refer [modal-component]]
        [tester-page-l.core.connection :refer [connection-list]]
        [tester-page-l.core.connection :refer [open-connection]]))

(defn navigation-buttons-component [] 
    [:form {:class "form-inline"}
        [:button {:class "form-inline btn btn-success my-2 my-sm-0 px-2 mr-1" :type "button"} "Login"]
        [:button {:class "form-inline btn btn-light my-2 my-sm-0 px-2 mr-1" :data-toggle "modal" :data-target "#connection-modal-id" :type "button"} "Connections"]
        [:button {:class "form-inline btn btn-dark my-2 my-sm-0 px-2 mr-1" :type "button"} "Refrersh API"]])

(defn connection-table []
    [:table {:class "table table-sm table-striped table-responsive-lg"}
        [:thead>tr 
            [:th {:scope "col"} "Name"]
            [:th {:scope "col"} "Url"]
            [:th {:scope "col"} "Secure"]
            [:th {:scope "col" :class "d-flex flex-row-reverse"} 
                [:button {:class "btn btn-light p-0 m-0 px-1"} "reset to default"]]]
        [:tbody
            (let [servers @connection-list]
                (for [server servers] ^{:key (get server :name)}
                    [:tr 
                        [:th {:scope "row"} (get server :name)]
                        [:td (get server :url)]
                        [:td (str (get server :secure))]
                        [:td {:class "d-flex flex-row-reverse"} 
                            [:button 
                                {
                                    :class "btn btn-secondary p-0 m-0 px-1"
                                    :on-click #((open-connection server))
                                } 
                                "select"]]]
                ))]])

(defn connection-add-form []
    [:p "Connection Add Form"])

(defn connection-info []
    [:p "Connection information"])

(defn connections-component []
    [:div {:class "p-1"}
        [:h5 "Connection List"]
        [:div {:class "row"} [connection-table]]
        [:div {:class "row"} [connection-add-form]]
        [:h5 "Connection Information"]
        [:div {:class "row"} [connection-info]]])

(defn navigation-component []
    [:nav {:class "navbar navbar-expand-lg navbar-dark bg-dark d-flex justify-content-between"}
        [:a {:class "navbar-brand" } "API Tester Page"]
        [navigation-buttons-component]
        [modal-component 
            "connection-modal-id" 
            "Connections" 
            "medium" 
            [connections-component]]])

