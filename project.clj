(defproject usps-clj "0.1.0"
  :description "Clojure interface to USPS's outdated API."
  :url "https://github.com/banzai-inc/usps-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.xml "0.0.7"]
                 [clj-http "0.7.2"]]
  :profiles {:dev {:dependencies [[environ "0.4.0"]]}})
