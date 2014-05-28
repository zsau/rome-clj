(defproject zsau/feedparser-clj "0.4.2"
  :description "Parse RSS/Atom feeds with a simple, clojure-friendly API. Forked from scsibug/feedparser-clj."
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [enlive "1.1.5"]
                 [net.java.dev.rome/rome "1.0.0"]]
  :main feedparser-clj.core
  :aot [feedparser-clj.core])
