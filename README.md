# rome-clj

rome-clj is a Clojure wrapper for ROME, a Java [RSS](https://en.wikipedia.org/wiki/RSS)/[Atom](https://en.wikipedia.org/wiki/Atom_(Web_standard)) feed parsing library. Aside from some light normalization such as merging `getAuthor` with `getAuthors`, we translate ROME's `Synd*` interfaces 1-1; see [ROME's javadocs](https://javadoc.io/static/com.rometools/rome/1.15.0/com/rometools/rome/feed/synd/package-summary.html) for more info.

Originally forked from [feedparser-clj](https://github.com/scsibug/feedparser-clj).

## Installation

Leiningen dependency:

```clojure
[zsau/rome-clj "1.0.2"]
```

## Usage

The public API contains only the function `parse`, which takes a `java.io.InputStream` and returns a map.

```clojure
=> (require '[rome-clj :as rome]
            '[clj-http.client :as http]
            '[clojure.pprint :as pp])
=> (-> "https://www.rssboard.org/files/sample-rss-2.xml"
       (http/get {:as :stream})
       :body
       rome/parse
       pp/pprint)
{:description "Liftoff to Space Exploration.",
 :feed-type "rss_2.0",
 :entries
 [{:description
   {:type "text/html",
    :value
    "How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>."},
   :published-date #inst "2003-06-03T09:39:21.000-00:00",
   :title "Star City",
   :link "http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp",
   :uri "http://liftoff.msfc.nasa.gov/2003/06/03.html#item573"}
  {:description
   {:type "text/html",
    :value
    "Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a <a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\">partial eclipse of the Sun</a> on Saturday, May 31st."},
   :published-date #inst "2003-05-30T11:06:42.000-00:00",
   :link "http://liftoff.msfc.nasa.gov/2003/05/30.html#item572",
   :uri "http://liftoff.msfc.nasa.gov/2003/05/30.html#item572"}
  {:description
   {:type "text/html",
    :value
    "Before man travels to Mars, NASA hopes to design new engines that will let us fly through the Solar System more quickly.  The proposed VASIMR engine would do that."},
   :published-date #inst "2003-05-27T08:37:32.000-00:00",
   :title "The Engine That Does More",
   :link "http://liftoff.msfc.nasa.gov/news/2003/news-VASIMR.asp",
   :uri "http://liftoff.msfc.nasa.gov/2003/05/27.html#item571"}
  {:description
   {:type "text/html",
    :value
    "Compared to earlier spacecraft, the International Space Station has many luxuries, but laundry facilities are not one of them.  Instead, astronauts have other options."},
   :published-date #inst "2003-05-20T08:56:02.000-00:00",
   :title "Astronauts' Dirty Laundry",
   :link "http://liftoff.msfc.nasa.gov/news/2003/news-laundry.asp",
   :uri "http://liftoff.msfc.nasa.gov/2003/05/20.html#item570"}],
 :published-date #inst "2003-06-10T04:00:00.000-00:00",
 :title "Liftoff News",
 :language "en-us",
 :link "http://liftoff.msfc.nasa.gov/",
 :authors [{:email "editor@example.com", :name "editor@example.com"}]}
```

License
-------

Copyright 2010 Greg Heartsfield, 2014 zsau. Distributed under the [3-Clause BSD License](LICENSE).
