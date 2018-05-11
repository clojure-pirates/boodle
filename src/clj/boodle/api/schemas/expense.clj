(ns boodle.api.schemas.expense
  (:require [schema.core :as schema]))

(def Response
  {:id schema/Int
   :date schema/Inst
   :category schema/Int
   :item schema/Str
   :amount schema/Str
   :from-savings schema/Bool})

(schema/defschema Body
  {:date schema/Inst
   :category schema/Int
   :item schema/Str
   :amount schema/Str
   :from-savings schema/Bool})

(schema/defschema ByDateAndCategories
  {:from schema/Str
   :to schema/Str
   :categories (schema/pred seq?)})
