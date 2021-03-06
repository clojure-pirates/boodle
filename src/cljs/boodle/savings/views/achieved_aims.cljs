(ns boodle.savings.views.achieved-aims
  (:require [boodle.common :as common]
            [boodle.i18n :refer [translate]]
            [boodle.transactions.views :as t]
            [boodle.validation :as v]
            [re-frame.core :as rf]))

(defn table
  []
  [t/transactions-table :achieved-aim-transactions])

(defn dropdown
  []
  (fn []
    (let [params (rf/subscribe [:aims-params])
          achieved-aims (conj @(rf/subscribe [:achieved-aims])
                              {:id "" :name ""})]
      [:nav.level
       [:div.level-item.has-text-centered
        [:div.field.is-horizontal
         [:div.field-label.is-normal
          [:label.label (translate :it :aims/label.achieved)]]
         [:div.field-body
          [:div.field
           [:div.select
            [:select
             {:value (v/or-empty-string (:achieved @params))
              :on-change #(rf/dispatch [:aims-change-achieved
                                        (-> % .-target .-value)])}
             (map common/render-option achieved-aims)]]]]]]])))
