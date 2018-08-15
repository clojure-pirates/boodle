(ns boodle.api.handlers
  (:require [boodle.api.resources.aim :as r.aim]
            [boodle.api.resources.category :as r.category]
            [boodle.api.resources.expense :as r.expense]
            [boodle.api.resources.report :as r.report]
            [boodle.api.resources.saving :as r.saving]
            [boodle.api.resources.transaction :as r.transaction]
            [boodle.api.schemas.aim :as s.aim]
            [boodle.api.schemas.category :as s.category]
            [boodle.api.schemas.expense :as s.expense]
            [boodle.api.schemas.report :as s.report]
            [boodle.api.schemas.saving :as s.saving]
            [boodle.api.schemas.transaction :as s.transaction]
            [compojure.api.sweet :as api]
            [ring.util.http-response :as response]))

(api/defapi apis
  {:coercion nil
   :swagger
   {:ui "/swagger-ui"
    :spec "/swagger.json"
    :data
    {:info
     {:version "0.0.1"
      :title "boodle APIs"
      :description "These are all the boodle APIs"}
     :tags [{:name "operations"} {:name "information"}]}}}

  (api/context "/api/category" [id category-name]
    :tags ["operations"]
    (api/GET "/find" []
      :return [s.category/Response]
      :summary "returns all the categories"
      (r.category/find-all))
    (api/GET "/find/:id" [id]
      :return s.category/Response
      :summary "returns the category identified by id"
      (r.category/find-by-id id))
    (api/POST "/insert" []
      :return s.category/Response
      :body [category s.category/Body]
      :summary "inserts a category"
      (r.category/insert! category))
    (api/PUT "/update" []
      :return s.category/Response
      :body [category s.category/Body]
      :summary "updates a category"
      (r.category/update! category))
    (api/DELETE "/delete/:id" [id]
      :summary "deletes the category identified by id"
      (r.category/delete! id)))

  (api/context "/api/expense" [id item]
    :tags ["operations"]
    (api/GET "/find" []
      :return [s.expense/Response]
      :summary "returns all the categories"
      (r.expense/find-all))
    (api/GET "/find/:id" [id]
      :return s.expense/Response
      :summary "returns the expense identified by id"
      (r.expense/find-by-id id))
    (api/POST "/find-by-date-and-categories" []
      :return s.expense/Response
      :body [expenses-by-date s.expense/ByDateAndCategories]
      :summary "returns the expenses filtered by date and categories"
      (r.expense/find-by-date-and-categories expenses-by-date))
    (api/POST "/insert" []
      :return s.expense/Response
      :body [expense s.expense/Body]
      :summary "inserts a expense"
      (r.expense/insert! expense))
    (api/PUT "/update" []
      :return s.expense/Response
      :body [expense s.expense/Body]
      :summary "updates an expense"
      (r.expense/update! expense))
    (api/DELETE "/delete/:id" [id]
      :summary "deletes the expense identified by id"
      (r.expense/delete! id)))

  (api/context "/api/aim" [id aim-name]
    :tags ["operations"]
    (api/GET "/find" []
      :return [s.aim/Response]
      :summary "returns all the aims"
      (r.aim/find-all))
    (api/GET "/find/:id" [id]
      :return s.aim/Response
      :summary "returns the aim identified by id"
      (r.aim/find-by-id id))
    (api/GET "/active" []
      :return [s.aim/Response]
      :summary "returns all the active aims"
      (r.aim/find-active))
    (api/GET "/achieved" []
      :return [s.aim/Response]
      :summary "returns all the achieved aims"
      (r.aim/find-achieved))
    (api/PUT "/achieved" []
      :return [s.aim/Response]
      :body [aim s.aim/Body]
      :summary "Mark an aim as achieved"
      (r.aim/achieved! aim))
    (api/POST "/insert" []
      :return s.aim/Response
      :body [aim s.aim/Body]
      :summary "inserts an aim"
      (r.aim/insert! aim))
    (api/PUT "/update" []
      :return s.aim/Response
      :body [aim s.aim/Body]
      :summary "updates an aim"
      (r.aim/update! aim))
    (api/DELETE "/delete/:id" [id]
      :summary "deletes the aim identified by id"
      (r.aim/delete! id))
    (api/GET "/transactions" []
      :return [s.aim/Response]
      :summary "returns all the aims with their transactions"
      (response/ok (r.aim/aims-with-transactions))))

  (api/context "/api/transaction" [id transaction-name]
    :tags ["operations"]
    (api/GET "/find" []
      :return [s.transaction/Response]
      :summary "returns all the transactions"
      (r.transaction/find-all))
    (api/GET "/find/:id" [id]
      :return s.transaction/Response
      :summary "returns the transaction identified by id"
      (r.transaction/find-by-id id))
    (api/GET "/aim/:id" [id]
      :return s.transaction/Response
      :summary "returns the transaction identified by aim id"
      (response/ok (r.transaction/find-by-aim id)))
    (api/POST "/insert" []
      :return s.transaction/Response
      :body [transaction s.transaction/Body]
      :summary "inserts a transaction"
      (r.transaction/insert! transaction))
    (api/PUT "/update" []
      :return s.transaction/Response
      :body [transaction s.transaction/Body]
      :summary "updates a transaction"
      (r.transaction/update! transaction))
    (api/DELETE "/delete/:id" [id]
      :summary "deletes the transaction identified by id"
      (r.transaction/delete! id)))

  (api/context "/api/saving" [id saving-name]
    :tags ["operations"]
    (api/GET "/find" []
      :return [s.saving/Response]
      :summary "returns all the savings"
      (response/ok (r.saving/find-all)))
    (api/GET "/find/:id" [id]
      :return s.saving/Response
      :summary "returns the saving identified by id"
      (r.saving/find-by-id id))
    (api/POST "/insert" []
      :return s.saving/Response
      :body [saving s.saving/Body]
      :summary "inserts a saving"
      (r.saving/insert! saving))
    (api/PUT "/update" []
      :return s.saving/Response
      :body [saving s.saving/Body]
      :summary "updates the saving identified by id"
      (r.saving/update! saving))
    (api/DELETE "/delete/:id" [id]
      :summary "deletes the saving identified by id"
      (r.saving/delete! id))
    (api/PUT "/transfer" []
      :return s.saving/Response
      :body [transfer s.saving/TransferBody]
      :summary "transfer money from saving to aim"
      (r.saving/transfer! transfer)))

  (api/context "/api/report" []
    :tags ["information"]
    (api/POST "/data" []
      :return [s.report/Response]
      :body [params s.report/Body]
      :summary "returns all the data for the required report"
      (let [categories (get params :categories nil)]
        (if (or (nil? categories) (empty? categories))
          (response/ok (r.report/find-totals-for-categories params))
          (response/ok (r.report/get-data params)))))))
