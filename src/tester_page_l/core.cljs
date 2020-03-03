(ns tester-page-l.core
    (:require 
      [reagent.core :as r]
      [tester-page-l.core.navigation :refer [navigation-component]]
      [tester-page-l.core.workspace :refer [workspace-component]]
      [tester-page-l.core.commands :refer [commands-component]]
      [tester-page-l.core.events :refer [events-component]]
      [tester-page-l.core.connection :refer [open-connection]]
      [tester-page-l.core.connection :refer [connection-selected]]))



;; -------------------------
;; Views

(defn home-page []
  [:div
    [navigation-component]
    [:div {:class "container-fluid row p-1 pt-3"}
      [:div {:class "col-2"} 
        [:h4 "Commands"]
        [commands-component]]
      [:div {:class "col-7"} 
        [:h4 "Workspace"]
        [workspace-component]]
      [:div {:class "col-3"} 
        [:h4 "Received Events"]
        [events-component]]]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (open-connection @connection-selected)
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
