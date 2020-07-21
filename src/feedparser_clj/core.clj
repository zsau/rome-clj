(ns feedparser-clj.core
  (:import (com.rometools.rome.feed.synd SyndCategory SyndContent SyndEnclosure SyndEntry SyndFeed SyndImage SyndLink SyndPerson)
           (com.rometools.rome.io SyndFeedInput XmlReader)
           (java.net URI)
           (java.io InputStream)
           (org.apache.http.impl.client HttpClients)
           (org.apache.http.client.methods HttpGet))
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

(defn empty->nil [c]
  (if (empty? c) nil c))

(defn make-enclosure [^SyndEnclosure e]
  (map->enclosure {:length (.getLength e)
                   :type (.getType e)
                   :url (.getUrl e)}))

(defn make-content [^SyndContent c]
  (map->content {:type (.getType c)
                 :value (.getValue c)}))

(defn text-content [c]
  (let [{:keys [type value]} (make-content c)]
    (if (not= "html" type) value
      (apply str (html/select (html/html-snippet value) [html/text-node])))))

(defn make-link [^SyndLink l]
  (map->link {:href (.getHref l)
              :hreflang (.getHreflang l)
              :length (.getLength l)
              :rel (.getRel l)
              :title (.getTitle l)
              :type (.getType l)}))

(defn make-category [^SyndCategory c]
  (map->category {:name (.getName c)
                  :taxonomy-uri (.getTaxonomyUri c)}))

(defn make-person [^SyndPerson p]
  (map->person {:email (.getEmail p)
                :name (.getName p)
                :uri (.getUri p)}))

(defn make-image [^SyndImage i]
  (map->image {:description (.getDescription i)
               :link (.getLink i)
               :title (.getTitle i)
               :url (.getUrl i)}))

(defn remove-parens [name]
  (some-> name
    (str/replace #"^\(" "")
    (str/replace #"\)$" "")))

(defn parse-rss-author [author]
  (if (str/includes? author "@")
    (let [[email name] (str/split author #" +" 2)]
      {:email email, :name (remove-parens name)})
    {:name author}))

(defn entry-authors [^SyndEntry e]
  (if-let [authors (seq (.getAuthors e))]
    (map make-person authors)
    (if-let [author (empty->nil (.getAuthor e))]
      [(parse-rss-author author)]
      [])))

(defn make-entry [^SyndEntry e]
  (map->entry {:authors (entry-authors e)
               :categories (map make-category (seq (.getCategories e)))
               :content (when-let [c (first (.getContents e))]
                          (make-content c))
               :contributors (map make-person (seq (.getContributors e)))
               :description (if-let [d (.getDescription e)] (make-content d))
               :enclosures (map make-enclosure (seq (.getEnclosures e)))
               :link (.getLink e)
               :published-date (.getPublishedDate e)
               :title (text-content (.getTitleEx e))
               :updated-date (.getUpdatedDate e)
               :uri (.getUri e)}))

(defn make-feed [^SyndFeed f]
  (map->feed {:authors (map make-person (seq (.getAuthors f)))
              :categories (map make-category (seq (.getCategories f)))
              :contributors (map make-person (seq (.getContributors f)))
              :copyright (.getCopyright f)
              :description (.getDescription f)
              :encoding (.getEncoding f)
              :entries (map make-entry (seq (.getEntries f)))
              :entry-links (map make-link (seq (.getLinks f)))
              :feed-type (.getFeedType f)
              :image (if-let [i (.getImage f)] (make-image i))
              :language (.getLanguage f)
              :link (.getLink f)
              :published-date (.getPublishedDate f)
              :title (text-content (.getTitleEx f))
              :uri (.getUri f)}))

(defn uri-stream [^URI uri]
  (let [client (-> (HttpClients/custom)
                   (.useSystemProperties)
                   (.disableCookieManagement)
                   (.build))
        response (.execute client (HttpGet. uri))]
    (.getContent (.getEntity response))))

(defn parse-feed [^InputStream istream]
  (make-feed
    (.build (SyndFeedInput.) (XmlReader. istream))))
