(ns usps-clj.address
  (:refer-clojure :exclude [get])
  (:require [clj-http.client :as http]
            [clojure.data.xml :refer :all]
            [clojure.string :as string]))

(def ^{:private true} bad-chars #"[&]")

(defn- remove-bad-chars [line]
  (string/replace line bad-chars ""))

(defn- get-response-contents [xml]
  (:content (first (:content xml))))

(defn- get-address-item [item el]
  (first (:content (first (filter #(= (:tag %) item) (get-response-contents el))))))

(defn- address-as-xml [address]
  (str "<Address>"
          "<Address1></Address1>"
          "<Address2>" (remove-bad-chars (:street address)) "</Address2>"
          "<City>" (:city address) "</City>"
          "<State>" (:state address) "</State>"
          "<Zip5>" (:zip address) "</Zip5>"
          "<Zip4></Zip4>"
        "</Address>"))

(defn- address-request
  [address usps-api-url usps-user-id]
  (str usps-api-url "?API=Verify&XML="
       "<AddressValidateRequest%20USERID=\"" usps-user-id "\">"
          (address-as-xml address)
       "</AddressValidateRequest>"))

(defn- has-error? [xml]
  (not (nil? (get-address-item :Error xml))))

(defn- missing-zip4? [xml]
  (nil? (get-address-item :Zip4 xml)))

(defn- parse-response [response]
  (let [ret (parse (java.io.StringReader. response))]
    (if (or (has-error? ret)
            (missing-zip4? ret)) nil 
      {:street (get-address-item :Address2 ret)
       :city (get-address-item :City ret)
       :state (get-address-item :State ret)
       :zip (get-address-item :Zip5 ret)
       :zip_four (get-address-item :Zip4 ret)})))

(defn validate-address
  "Validate an address with USPS."
  [address usps-api-url usps-user-id]
  (parse-response (:body (http/get (address-request address
                                                    usps-api-url
                                                    usps-user-id)))))
