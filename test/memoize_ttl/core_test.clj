(ns memoize-ttl.core-test
  (:require [midje.sweet :refer :all]
            [memoize-ttl.core :as core]))

(def expensive-fn (fn [_] (throw (Exception. "Should never be called"))))

(facts "about memoize-ttl"
  (fact "caches values"
    (let [TTL 50
          memoized (core/memoize-ttl #(expensive-fn %) TTL)]
      (memoized ::key)
      => "value"
      (provided
        (expensive-fn ::key)
        => "value")

      (memoized ::key)
      => "value"
      (provided
        (expensive-fn anything)
        => irrelevant :times 0)

      (Thread/sleep TTL)

      (memoized ::key)
      => "value"
      (provided
        (expensive-fn ::key)
        => "value") )))
