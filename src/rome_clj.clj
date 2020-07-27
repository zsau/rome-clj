(ns rome-clj
  (:import (com.rometools.rome.feed.synd SyndCategory SyndContent SyndEnclosure SyndEntry SyndFeed SyndImage SyndLink SyndPerson)
           (com.rometools.rome.io SyndFeedInput XmlReader)
           (java.net URI)
           (java.io InputStream))
  (:require [clojure.string :as str]
            [net.cgrand.enlive-html :as html]))

;; Source: https://stackoverflow.com/a/201378
(def email-pattern
  #"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])")

(def name-and-email-pattern
  (re-pattern (format "(.*) <(%s)>" email-pattern)))

(def email-and-name-pattern
  (re-pattern (format "(%s) \\((.*)\\)" email-pattern)))

(defprotocol ToClj
  (->clj* [this]))

(defn empty->nil [x]
  (when-not (and (coll? x) (empty? x))
    x))

(defn ->clj [synd-obj]
  (into {} (filter (comp empty->nil val))
        (->clj* synd-obj)))

(defn parse-author [author]
  (if-let [[_ name email] (re-matches name-and-email-pattern author)]
    {:email email, :name name}
    (if-let [[_ email & more] (re-matches email-and-name-pattern author)]
      {:email email, :name (last more)}
      (if (re-matches email-pattern author)
        {:email author}
        {:name author}))))

(defn feed-authors [^SyndFeed f]
  (or (some->> f .getAuthors not-empty (mapv ->clj))
      (some-> f .getAuthor not-empty parse-author vector)
      []))

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
    (->clj* [c]
      {:name (.getName c)
       :taxonomy-uri (.getTaxonomyUri c)})
  SyndContent
    (->clj* [c]
      {:type (.getType c)
       :value (.getValue c)})
  SyndEnclosure
    (->clj* [e]
      {:length (.getLength e)
       :type (.getType e)
       :url (.getUrl e)})
  SyndEntry
    (->clj* [e]
      {:authors (entry-authors e)
       :categories (mapv ->clj (.getCategories e))
       :content (some-> e .getContents first ->clj)
       :contributors (mapv ->clj (.getContributors e))
       :description (some-> e .getDescription ->clj)
       :enclosures (mapv ->clj (.getEnclosures e))
       :link (.getLink e)
       :published-date (.getPublishedDate e)
       :title (text-content (.getTitleEx e))
       :updated-date (.getUpdatedDate e)
       :uri (.getUri e)})
  SyndFeed
    (->clj* [f]
      {:authors (feed-authors f)
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
       :uri (.getUri f)})
  SyndImage
    (->clj* [i]
      {:description (.getDescription i)
       :link (.getLink i)
       :title (.getTitle i)
       :url (.getUrl i)})
  SyndLink
    (->clj* [l]
      {:href (.getHref l)
       :hreflang (.getHreflang l)
       :length (.getLength l)
       :rel (.getRel l)
       :title (.getTitle l)
       :type (.getType l)})
  SyndPerson
    (->clj* [p]
      {:email (.getEmail p)
       :name (.getName p)
       :uri (.getUri p)}))

;;; Public API

(defn parse
  "Parses an RSS or Atom feed from `istream`, returning a map."
  [^InputStream istream]
  (-> (SyndFeedInput.)
      (.build (XmlReader. istream))
      ->clj))
