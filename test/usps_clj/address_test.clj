(ns usps-clj.address-test
  (:require [clojure.test :refer :all]
            [usps-clj.address :refer :all]
            [environ.core :refer :all]))

(deftest addresses
  (let [usps-api-url (env :usps-api-url)
        usps-user-id (env :usps-user-id)]

    (testing "verify address"
      (is (= {:street "6406 IVY LN" :city "GREENBELT" :state "MD" :zip "20770" :zip_four "1441"}
             (validate-address
               {:street "6406 ivy lane" :city "Greenbelt" :state "MD" :zip ""}
               usps-api-url usps-user-id))))

    (testing "remove bad characters"
      (is (= {:street "6406 IVY LN" :city "GREENBELT" :state "MD" :zip "20770" :zip_four "1441"}
             (validate-address
               {:street "6406 IVY & LANE" :city "Greenbelt" :state "MD" :zip ""}
               usps-api-url usps-user-id))))

    (testing "address has an error"
      (is (= nil (validate-address {:street "No Way Out"}
                                   usps-api-url
                                   usps-user-id))))

    (testing "address missing zip4"
      (is (= nil (validate-address
                   {:street "963 E 970 N" :state "UT" :city "Orem" :zip "84097"}
                   usps-api-url
                   usps-user-id))))))
