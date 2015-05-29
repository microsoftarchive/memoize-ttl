

# memoize-ttl [![Build Status](https://travis-ci.org/wunderlist/memoize-ttl.svg)](https://travis-ci.org/wunderlist/memoize-ttl)

memoize-ttl provides a simple interface similar to Clojure's builtin [`memoize` function](http://clojuredocs.org/clojure.core/memoize).
Additionally there is a time-to-live (TTL) value when cached entries expire
and a `invalidate` function to manually invalidate the cache, very useful for unit testing.


## Releases and Dependency Information

memoize-ttl is released via [Clojars](https://clojars.org/memoize-ttl). The Latest stable release is 0.0.1

[Leiningen](https://github.com/technomancy/leiningen) dependency information:

```clojure
[memoize-ttl "0.0.1"]
```

Maven dependency information:

```xml
<dependency>
  <groupId>memoize-ttl</groupId>
  <artifactId>memoize-ttl</artifactId>
  <version>0.0.1</version>
</dependency>
```


## License

Copyright © 2015 6 Wunderkinder GmbH.

Distributed under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html), the same as Clojure.
