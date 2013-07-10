# usps-clj

A Clojure library designed to interface with USPS's outdated API.

## Install

``` clojure
[usps-clj "0.1.0-SNAPSHOT"]
```

## Usage

#### Validate an address

``` clojure
(validate-address
  {:street "963 E 970 N" :state "UT" :city "Orem" :zip "84097"}
  usps-api-url
  usps-user-id)
```

Returns the validated address; returns nil if USPS reports an error.

## License

Copyright © 2013 Banzai Inc.

Distributed under the Eclipse Public License, the same as Clojure.
