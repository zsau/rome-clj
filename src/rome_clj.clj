(ns rome-clj
  (:import (com.rometools.rome.feed.synd SyndCategory SyndContent SyndEnclosure SyndEntry SyndFeed SyndImage SyndLink SyndPerson)
           (com.rometools.rome.io SyndFeedInput XmlReader)
           (java.net URI)
           (java.io InputStream))
  (:require [clojure.string :as str]
            [net.cgrand.enlive-html :as html])
  (:gen-class))

(defrecord feed [authors categories contributors copyright description
                 encoding entries feed-type image language link entry-links
                 published-date title uri])
(defrecord entry [authors categories content contributors description
                  enclosures link published-date title updated-date url])
(defrecord enclosure [length type uri])
(defrecord person [email name uri])
(defrecord category [name taxonomy-uri])
(defrecord content [type value])
(defrecord image [description link title url])
(defrecord link [href hreflang length rel title type])

(defprotocol ToClj
  (->clj [this]))

(defn remove-parens [s]
  (str/replace s #"^\((.*)\)$" "$1"))

(defn parse-author [author]
  (if (str/includes? author "@")
    (let [[email name] (str/split author #" +" 2)]
      {:email email
       :name (some-> name remove-parens)})
    {:name author}))

(defn entry-authors [^SyndEntry e]
  (or (some->> e .getAuthors not-empty (mapv ->clj))
      (some-> e .getAuthor not-empty parse-author vector)
      []))

(defn text-content [c]
  (let [{:keys [type value]} (->clj c)]
    (if (not= "html" type) value
      (apply str (html/select (html/html-snippet value) [html/text-node])))))

(extend-protocol ToClj
  SyndCategory
    (->clj [c]
      (map->category {:name (.getName c)
                      :taxonomy-uri (.getTaxonomyUri c)}))
  SyndContent
    (->clj [c]
      (map->content {:type (.getType c)
                     :value (.getValue c)}))
  SyndEnclosure
    (->clj [e]
      (map->enclosure {:length (.getLength e)
                       :type (.getType e)
                       :url (.getUrl e)}))
  SyndEntry
    (->clj [e]
      (map->entry {:authors (entry-authors e)
                   :categories (mapv ->clj (.getCategories e))
                   :content (some-> e .getContents first ->clj)
                   :contributors (mapv ->clj (.getContributors e))
                   :description (some-> e .getDescription ->clj)
                   :enclosures (mapv ->clj (.getEnclosures e))
                   :link (.getLink e)
                   :published-date (.getPublishedDate e)
                   :title (text-content (.getTitleEx e))
                   :updated-date (.getUpdatedDate e)
                   :uri (.getUri e)}))
  SyndFeed
    (->clj [f]
      (map->feed {:authors (mapv ->clj (.getAuthors f))
                  :categories (mapv ->clj (.getCategories f))
                  :contributors (mapv ->clj (.getContributors f))
                  :copyright (.getCopyright f)
                  :description (.getDescription f)
                  :encoding (.getEncoding f)
                  :entries (mapv ->clj (.getEntries f))
                  :entry-links (mapv ->clj (.getLinks f))
                  :feed-type (.getFeedType f)
                  :image (some-> f .getImage ->clj)
                  :language (.getLanguage f)
                  :link (.getLink f)
                  :published-date (.getPublishedDate f)
                  :title (text-content (.getTitleEx f))
                  :uri (.getUri f)}))
  SyndImage
    (->clj [i]
      (map->image {:description (.getDescription i)
                   :link (.getLink i)
                   :title (.getTitle i)
                   :url (.getUrl i)}))
  SyndLink
    (->clj [l]
      (map->link {:href (.getHref l)
                  :hreflang (.getHreflang l)
                  :length (.getLength l)
                  :rel (.getRel l)
                  :title (.getTitle l)
                  :type (.getType l)}))
  SyndPerson
    (->clj [p]
      (map->person {:email (.getEmail p)
                    :name (.getName p)
                    :uri (.getUri p)})))

(defn parse [^InputStream istream]
  (-> (SyndFeedInput.)
      (.build (XmlReader. istream))
      ->clj))
