(defproject zsau/rome-clj "1.0.3"
  :description "Clojure wrapper for ROME, a Java RSS/Atom feed parsing library"
  :profiles {:dev {:resource-paths ["test/resources"]}}
  :dependencies [[com.rometools/rome "1.15.0"]
                 [enlive "1.1.6"]
                 [org.clojure/clojure "1.10.1"]])
