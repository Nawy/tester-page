(ns tester-page-l.core
    (:require 
      [reagent.core :as r]
      [tester-page-l.core.navigation :refer [navigation-component]]
      [tester-page-l.core.workspace :refer [workspace-component]]
      [tester-page-l.core.commands :refer [commands-component]]
      [tester-page-l.core.events :refer [events-component]]))



;; -------------------------
;; Views

(defn home-page []
  [:div
    [navigation-component]
    [:div {:class "container-fluid row p-1"}
      [:div {:class "col-3"} 
        [:h3 "Commands"]
        [commands-component]]
      [:div {:class "col-6"} 
        [:h3 "Workspace"]
        [workspace-component]]
      [:div {:class "col-3"} 
        [:h3 "Received Events"]
        [events-component]]]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
