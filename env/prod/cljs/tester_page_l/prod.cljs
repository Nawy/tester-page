(ns tester-page-l.prod
  (:require
    [tester-page-l.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
