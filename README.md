# ip-validation

Provides validation constraints that work on both [CharSequence](https://docs.oracle.com/javase/8/docs/api/java/lang/CharSequence.html) and [IPAddress](https://robtimus.github.io/ip-utils/apidocs/com/github/robtimus/net/ip/IPAddress.html). These not only allow you to validate that a value is a valid IP address, but also that the IP address is contained in a specific IP range or subnet.

## Bean Validation API support

This library has been written for [Bean Validation 1.1](https://beanvalidation.org/1.1/). However, it has also been tested with [Bean Validation 2.0](https://beanvalidation.org/2.0/). The [javax.validation:validation-api](https://search.maven.org/search?q=g:javax.validation%20AND%20a:validation-api) dependency is a provided dependency, allowing you to provide your own version without any conflicts.
