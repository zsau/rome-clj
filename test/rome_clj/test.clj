(ns rome-clj.test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]
            [rome-clj :as rome]))

(defn slurp-feed [name]
  (with-open [istream (io/input-stream (io/resource (str name ".xml")))]
    (rome/parse istream)))

;; Source: https://tools.ietf.org/html/rfc4287#section-1.1
(deftest atom-1_0
  (is (= (slurp-feed "atom-1_0")
         {:description "\n    A <em>lot</em> of effort\n    went into making this effortless\n  "
          :encoding nil
          :feed-type "atom_1.0"
          :entries [{:description nil
                     :updated-date #inst "2005-07-31T12:29:29.000-00:00"
                     :content {:type "xhtml"
                               :value "\r\n      <div xmlns=\"http://www.w3.org/1999/xhtml\">\r\n        <p><i>[Update: The Atom draft is finished.]</i></p>\r\n      </div>\r\n    "}
                     :published-date #inst "2003-12-13T12:29:29.000-00:00"
                     :title "Atom draft-07 snapshot"
                     :categories []
                     :link "http://example.org/2005/04/02/atom"
                     :contributors [{:email nil
                                     :name "Sam Ruby"
                                     :uri nil}
                                    {:email nil
                                     :name "Joe Gregorio"
                                     :uri nil}]
                     :uri "tag:example.org,2003:3.2397"
                     :authors [{:email "f8dy@example.com"
                                :name "Mark Pilgrim"
                                :uri "http://example.org/"}]
                     :enclosures [{:length 1337
                                   :type "audio/mpeg"
                                   :url "http://example.org/audio/ph34r_my_podcast.mp3"}]}]
          :copyright "Copyright (c) 2003, Mark Pilgrim"
          :published-date #inst "2005-07-31T12:29:29.000-00:00"
          :entry-links [{:href "http://example.org/"
                         :hreflang "en"
                         :length 0
                         :rel "alternate"
                         :title nil
                         :type "text/html"}
                        {:href "http://example.org/feed.atom"
                         :hreflang nil
                         :length 0
                         :rel "self"
                         :title nil
                         :type "application/atom+xml"}]
          :title "dive into mark"
          :categories []
          :language nil
          :link "http://example.org/"
          :contributors []
          :image nil
          :uri "tag:example.org,2003:3"
          :authors []})))

;; Source: https://www.rssboard.org/files/sample-rss-092.xml
(deftest rss-0_92
  (is (= (slurp-feed "rss-0_92")
         {:description
          "A high-fidelity Grateful Dead song every day. This is where we're experimenting with enclosures on RSS news items that download when you're not using your computer. If it works (it will) it will be the end of the Click-And-Wait multimedia experience on the Internet. ",
          :encoding nil,
          :feed-type "rss_0.92",
          :entries
          [{:description
            {:type "text/html",
             :value
             "It's been a few days since I added a song to the Grateful Dead channel. Now that there are all these new Radio users, many of whom are tuned into this channel (it's #16 on the hotlist of upstreaming Radio users, there's no way of knowing how many non-upstreaming users are subscribing, have to do something about this..). Anyway, tonight's song is a live version of Weather Report Suite from Dick's Picks Volume 7. It's wistful music. Of course a beautiful song, oft-quoted here on Scripting News. <i>A little change, the wind and rain.</i>\n"},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 6182912,
              :type "audio/mpeg",
              :url
              "http://www.scripting.com/mp3s/weatherReportDicksPicsVol7.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "Kevin Drennan started a <a href=\"http://deadend.editthispage.com/\">Grateful Dead Weblog</a>. Hey it's cool, he even has a <a href=\"http://deadend.editthispage.com/directory/61\">directory</a>. <i>A Frontier 7 feature.</i>"},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://arts.ucsc.edu/GDead/AGDL/other1.html\">The Other One</a>, live instrumental, One From The Vault. Very rhythmic very spacy, you can listen to it many times, and enjoy something new every time."},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 6666097,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/theOtherOne.mp3"}]}
           {:description
            {:type "text/html",
             :value "This is a test of a change I just made. Still diggin.."},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "The HTML rendering almost <a href=\"http://validator.w3.org/check/referer\">validates</a>. Close. Hey I wonder if anyone has ever published a style guide for ALT attributes on images? What are you supposed to say in the ALT attribute? I sure don't know. If you're blind send me an email if u cn rd ths. "},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://www.cs.cmu.edu/~mleone/gdead/dead-lyrics/Franklin's_Tower.txt\">Franklin's Tower</a>, a live version from One From The Vault."},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 6701402,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/franklinsTower.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "Moshe Weitzman says Shakedown Street is what I'm lookin for for tonight. I'm listening right now. It's one of my favorites. \"Don't tell me this town ain't got no heart.\" Too bright. I like the jazziness of Weather Report Suite. Dreamy and soft. How about The Other One? \"Spanish lady come to me..\""},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://www.scripting.com/mp3s/youWinAgain.mp3\">The news is out</a>, all over town..<p>\nYou've been seen, out runnin round. <p>\nThe lyrics are <a href=\"http://www.cs.cmu.edu/~mleone/gdead/dead-lyrics/You_Win_Again.txt\">here</a>, short and sweet. <p>\n<i>You win again!</i>\n"},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 3874816,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/youWinAgain.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://www.getlyrics.com/lyrics/grateful-dead/wake-of-the-flood/07.htm\">Weather Report Suite</a>: \"Winter rain, now tell me why, summers fade, and roses die? The answer came. The wind and rain. Golden hills, now veiled in grey, summer leaves have blown away. Now what remains? The wind and rain.\""},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 12216320,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/weatherReportSuite.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://arts.ucsc.edu/gdead/agdl/darkstar.html\">Dark Star</a> crashes, pouring its light into ashes."},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 10889216,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/darkStar.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "DaveNet: <a href=\"http://davenet.userland.com/2001/01/21/theUsBlues\">The U.S. Blues</a>."},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "Still listening to the US Blues. <i>\"Wave that flag, wave it wide and high..\"</i> Mistake made in the 60s. We gave our country to the assholes. Ah ah. Let's take it back. Hey I'm still a hippie. <i>\"You could call this song The United States Blues.\"</i>"},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://www.sixties.com/html/garcia_stack_0.html\"><img src=\"http://www.scripting.com/images/captainTripsSmall.gif\" height=\"51\" width=\"42\" border=\"0\" hspace=\"10\" vspace=\"10\" align=\"right\"></a>In celebration of today's inauguration, after hearing all those great patriotic songs, America the Beautiful, even The Star Spangled Banner made my eyes mist up. It made my choice of Grateful Dead song of the night realllly easy. Here are the <a href=\"http://searchlyrics2.homestead.com/gd_usblues.html\">lyrics</a>. Click on the audio icon to the left to give it a listen. \"Red and white, blue suede shoes, I'm Uncle Sam, how do you do?\" It's a different kind of patriotic music, but man I love my country and I love Jerry and the band. <i>I truly do!</i>"},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 5272510,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/usBlues.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "Grateful Dead: \"Tennessee, Tennessee, ain't no place I'd rather be.\""},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 3442648,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/tennesseeJed.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "Ed Cone: \"Had a nice Deadhead experience with my wife, who never was one but gets the vibe and knows and likes a lot of the music. Somehow she made it to the age of 40 without ever hearing Wharf Rat. We drove to Jersey and back over Christmas with the live album commonly known as Skull and Roses in the CD player much of the way, and it was cool to see her discover one the band's finest moments. That song is unique and underappreciated. Fun to hear that disc again after a few years off -- you get Jerry as blues-guitar hero on Big Railroad Blues and a nice version of Bertha.\""},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 27503386,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/darkStarWharfRat.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://arts.ucsc.edu/GDead/AGDL/fotd.html\">Tonight's Song</a>: \"If I get home before daylight I just might get some sleep tonight.\" "},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 3219742,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/friendOfTheDevil.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://arts.ucsc.edu/GDead/AGDL/uncle.html\">Tonight's song</a>: \"Come hear Uncle John's Band by the river side. Got some things to talk about here beside the rising tide.\""},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 4587102,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/uncleJohnsBand.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://www.cs.cmu.edu/~mleone/gdead/dead-lyrics/Me_and_My_Uncle.txt\">Me and My Uncle</a>: \"I loved my uncle, God rest his soul, taught me good, Lord, taught me all I know. Taught me so well, I grabbed that gold and I left his dead ass there by the side of the road.\"\n"},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 2949248,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/meAndMyUncle.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "Truckin, like the doo-dah man, once told me gotta play your hand. Sometimes the cards ain't worth a dime, if you don't lay em down."},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 4847908,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/truckin.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "Two-Way-Web: <a href=\"http://www.thetwowayweb.com/payloadsForRss\">Payloads for RSS</a>. \"When I started talking with Adam late last year, he wanted me to think about high quality video on the Internet, and I totally didn't want to hear about it.\""},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value "A touch of gray, kinda suits you anyway.."},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 5588242,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/touchOfGrey.mp3"}]}
           {:description
            {:type "text/html",
             :value
             "<a href=\"http://www.sixties.com/html/garcia_stack_0.html\"><img src=\"http://www.scripting.com/images/captainTripsSmall.gif\" height=\"51\" width=\"42\" border=\"0\" hspace=\"10\" vspace=\"10\" align=\"right\"></a>In celebration of today's inauguration, after hearing all those great patriotic songs, America the Beautiful, even The Star Spangled Banner made my eyes mist up. It made my choice of Grateful Dead song of the night realllly easy. Here are the <a href=\"http://searchlyrics2.homestead.com/gd_usblues.html\">lyrics</a>. Click on the audio icon to the left to give it a listen. \"Red and white, blue suede shoes, I'm Uncle Sam, how do you do?\" It's a different kind of patriotic music, but man I love my country and I love Jerry and the band. <i>I truly do!</i>"},
            :updated-date nil,
            :content nil,
            :published-date nil,
            :title nil,
            :categories [],
            :link nil,
            :contributors [],
            :uri nil,
            :authors [],
            :enclosures
            [{:length 5272510,
              :type "audio/mpeg",
              :url "http://www.scripting.com/mp3s/usBlues.mp3"}]}],
          :copyright nil,
          :published-date #inst "2001-04-13T19:23:02.000-00:00",
          :entry-links [],
          :title "Dave Winer: Grateful Dead",
          :categories [],
          :language nil,
          :link "http://www.scripting.com/blog/categories/gratefulDead.html",
          :contributors [],
          :image nil,
          :uri nil,
          :authors [{:email "dave@userland.com"
                     :name "Dave Winer"}]})))

;; Source: https://www.rssboard.org/files/sample-rss-2.xml
(deftest rss-2_0
  (is (= (slurp-feed "rss-2_0")
         {:description "Liftoff to Space Exploration.",
          :encoding nil,
          :feed-type "rss_2.0",
          :entries
          [{:description
            {:type "text/html",
             :value
             "How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>."},
            :updated-date nil,
            :content nil,
            :published-date #inst "2003-06-03T09:39:21.000-00:00",
            :title "Star City",
            :categories [],
            :link "http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp",
            :contributors [],
            :uri "http://liftoff.msfc.nasa.gov/2003/06/03.html#item573",
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a <a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\">partial eclipse of the Sun</a> on Saturday, May 31st."},
            :updated-date nil,
            :content nil,
            :published-date #inst "2003-05-30T11:06:42.000-00:00",
            :title nil,
            :categories [],
            :link "http://liftoff.msfc.nasa.gov/2003/05/30.html#item572",
            :contributors [],
            :uri "http://liftoff.msfc.nasa.gov/2003/05/30.html#item572",
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "Before man travels to Mars, NASA hopes to design new engines that will let us fly through the Solar System more quickly.  The proposed VASIMR engine would do that."},
            :updated-date nil,
            :content nil,
            :published-date #inst "2003-05-27T08:37:32.000-00:00",
            :title "The Engine That Does More",
            :categories [],
            :link "http://liftoff.msfc.nasa.gov/news/2003/news-VASIMR.asp",
            :contributors [],
            :uri "http://liftoff.msfc.nasa.gov/2003/05/27.html#item571",
            :authors [],
            :enclosures []}
           {:description
            {:type "text/html",
             :value
             "Compared to earlier spacecraft, the International Space Station has many luxuries, but laundry facilities are not one of them.  Instead, astronauts have other options."},
            :updated-date nil,
            :content nil,
            :published-date #inst "2003-05-20T08:56:02.000-00:00",
            :title "Astronauts' Dirty Laundry",
            :categories [],
            :link "http://liftoff.msfc.nasa.gov/news/2003/news-laundry.asp",
            :contributors [],
            :uri "http://liftoff.msfc.nasa.gov/2003/05/20.html#item570",
            :authors [],
            :enclosures []}],
          :copyright nil,
          :published-date #inst "2003-06-10T04:00:00.000-00:00",
          :entry-links [],
          :title "Liftoff News",
          :categories [],
          :language "en-us",
          :link "http://liftoff.msfc.nasa.gov/",
          :contributors [],
          :image nil,
          :uri nil,
          :authors [{:email "editor@example.com"
                     :name "editor@example.com"}]})))
