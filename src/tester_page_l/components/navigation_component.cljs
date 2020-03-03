(ns tester-page-l.core.navigation
    (:require 
        [tester-page-l.core.helpers.modal :refer [modal-component]]
        [tester-page-l.core.connection :refer [connection-list]]
        [tester-page-l.core.connection :refer [connection-id]]
        [tester-page-l.core.connection :refer [connection-status]]
        [tester-page-l.core.connection :refer [connection-selected]]
        [tester-page-l.core.connection :refer [open-connection]]))
        

(defn get-connection-status-icon [status connection]
    (if (true? status)
        [:span [:b {:class "icon-success px-1"} "V"] (str "Connetions(" (get connection :name) ")")]
        [:span [:b {:class "icon-danger px-1"} "X"] (str "Connetions(" (get connection :name) ")")]))

(defn get-connection-status-string [status connection]
    (if (true? status)
        [:span (str "Connected to " (get connection :name))]
        [:span (str "Disconnected to " (get connection :name))]))

(defn navigation-buttons-component [] 
    [:form {:class "form-inline"}
        [:button {:class "form-inline btn btn-success my-2 my-sm-0 px-2 mr-1" :type "button"} "Login"]
        [:button 
            {:class "form-inline btn btn-light my-2 my-sm-0 px-2 mr-1" :data-toggle "modal" :data-target "#connection-modal-id" :type "button"} 
            (get-connection-status-icon @connection-status @connection-selected)]
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
                            (if (true? (get server :selected))
                                [:button 
                                    {
                                        :class "btn btn-success disabled p-0 m-0 px-1"
                                    } 
                                    "connected"]
                                [:button 
                                    {
                                        :class "btn btn-secondary p-0 m-0 px-1"
                                        :on-click #(open-connection server)
                                    } 
                                    "select"])]]
                ))]])

(defn connection-add-form []
    [:p (str "Connection Add Form " @connection-id)])

(defn connection-info []
    [:dl {:class "row"}
        [:dt {:class "col-3"} "Connection ID"]
        [:dd {:class "col-9"} [:code (str @connection-id)]]])

(defn connections-component []
    [:div {:class "p-1"}
        [:h5 "Connection List"]
        [:div {:class "row"} [connection-table]]
        [:div {:class "row"} [connection-add-form]]
        [:h5 "Connection Information"]
        [connection-info]])

(defn navigation-component []
    [:nav {:class "navbar navbar-expand-lg navbar-dark bg-dark d-flex justify-content-between"}
        [:a {:class "navbar-brand" } "API Tester Page"]
        [navigation-buttons-component]
        [modal-component 
            "connection-modal-id" 
            (get-connection-status-string @connection-status @connection-selected)
            "medium" 
            [connections-component]]])

