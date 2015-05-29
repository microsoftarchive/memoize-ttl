(ns memoize-ttl.core
  (:require [clojure.core.cache :as cache]))

(defn- make-cache
  [ttl-ms]
  (cache/ttl-cache-factory {} :ttl ttl-ms))

(defn invalidate
  "Given a memoized function, invalidate it's cache."
  [memoized-function]
  (if-let [cache-atom (-> memoized-function meta ::cache-atom)]
    (let [new (make-cache (-> memoized-function meta ::ttl-ms))]
      (swap! cache-atom (fn [old] new)))
    (throw (ex-info "No cache found to invalidate" {:on-function memoized-function})))
  nil)

(defn memoize-ttl
  "Decorate `function` and return a new function that
   will lookup it's arguments in a TTL cache and only
   if cach misses, use `function` to generate the value.

   The main reason for rolling our own was that core.memoize
   did not have a invalidate method and could not handle
   Midje's metaconstants as cache keys."
  [function ttl-ms]
  (let [ttl-cache (atom (make-cache ttl-ms))]
    (with-meta
      (fn [cache-key]
        (let [c @ttl-cache]
          (if (cache/has? c cache-key)
            (let [cached-value (get c cache-key)]
              (swap! ttl-cache #(cache/hit % cache-key))
              cached-value)
            (let [calculated-value (function cache-key)]
              (swap! ttl-cache #(cache/miss % cache-key calculated-value))
              calculated-value))))
      {::cache-atom ttl-cache
       ::ttl-ms ttl-ms} )))
