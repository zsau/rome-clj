# rome-clj

rome-clj is a Clojure wrapper for ROME, a Java [RSS](https://en.wikipedia.org/wiki/RSS)/[Atom](https://en.wikipedia.org/wiki/Atom_(Web_standard)) feed parsing library. Aside from some light normalization such as merging `getAuthor` with `getAuthors`, we translate ROME's `Synd*` interfaces 1-1; see [ROME's javadocs](https://javadoc.io/static/com.rometools/rome/1.15.0/com/rometools/rome/feed/synd/package-summary.html) for more info.

## Installation

Leiningen dependency:

```clojure
[zsau/rome-clj "1.0.0"]
```

## Usage

The public API contains only the function `parse`, which takes a `java.io.InputStream` and returns a map.

```clojure
=> (require '[rome-clj :as rome] '[clojure.java.io :as io] '[clojure.pprint :as pp])
=> (with-open [istream (io/input-stream "atom.xml")] (pp/pprint (rome/parse istream)))

{:authors [],
 :categories [],
 :contributors [],
 :copyright "Copyright (c) 2003, Mark Pilgrim",
 :description
 "\n    A <em>lot</em> of effort\n    went into making this effortless\n  ",
 :encoding nil,
 :entries
 [{:authors
   [{:email "f8dy@example.com",
     :name "Mark Pilgrim",
     :uri "http://example.org/"}],
   :categories [],
   :content
   {:type "xhtml",
    :value
    "\r\n      <div xmlns=\"http://www.w3.org/1999/xhtml\">\r\n        <p><i>[Update: The Atom draft is finished.]</i></p>\r\n      </div>\r\n    "},
   :contributors
   [{:email nil, :name "Sam Ruby", :uri nil}
    {:email nil, :name "Joe Gregorio", :uri nil}],
   :description nil,
   :enclosures
   [{:length 1337,
     :type "audio/mpeg",
     :uri nil,
     :url "http://example.org/audio/ph34r_my_podcast.mp3"}],
   :link "http://example.org/2005/04/02/atom",
   :published-date #inst "2003-12-13T12:29:29.000-00:00",
   :title "Atom draft-07 snapshot",
   :updated-date #inst "2005-07-31T12:29:29.000-00:00",
   :url nil,
   :uri "tag:example.org,2003:3.2397"}],
 :feed-type "atom_1.0",
 :image nil,
 :language nil,
 :link "http://example.org/",
 :entry-links
 [{:href "http://example.org/",
   :hreflang "en",
   :length 0,
   :rel "alternate",
   :title nil,
   :type "text/html"}
  {:href "http://example.org/feed.atom",
   :hreflang nil,
   :length 0,
   :rel "self",
   :title nil,
   :type "application/atom+xml"}],
 :published-date #inst "2005-07-31T12:29:29.000-00:00",
 :title "dive into mark",
 :uri "tag:example.org,2003:3"}
```

License
-------

Copyright 2010 Greg Heartsfield, 2014 zsau. Distributed under the [3-Clause BSD License](LICENSE).
