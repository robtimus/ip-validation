# ip-validation
[![Maven Central](https://img.shields.io/maven-central/v/com.github.robtimus/ip-validation)](https://search.maven.org/artifact/com.github.robtimus/ip-validation)
[![Build Status](https://github.com/robtimus/ip-validation/actions/workflows/build.yml/badge.svg)](https://github.com/robtimus/ip-validation/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.robtimus%3Aip-validation&metric=alert_status)](https://sonarcloud.io/summary/overall?id=com.github.robtimus%3Aip-validation)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.robtimus%3Aip-validation&metric=coverage)](https://sonarcloud.io/summary/overall?id=com.github.robtimus%3Aip-validation)
[![Known Vulnerabilities](https://snyk.io/test/github/robtimus/ip-validation/badge.svg)](https://snyk.io/test/github/robtimus/ip-validation)

Provides validation constraints that work on both [CharSequence](https://docs.oracle.com/javase/8/docs/api/java/lang/CharSequence.html) and [IPAddress](https://robtimus.github.io/ip-utils/apidocs/com/github/robtimus/net/ip/IPAddress.html). These not only allow you to validate that a value is a valid IP address, but also that the IP address is contained in a specific IP range or subnet.

## Bean Validation API support

Version 2.x of this library has been written for [Jakarta Bean Validation 3.0](https://beanvalidation.org/3.0/), as part of Jakarta EE 9.

Version 1.x of this library has been written for [Jakarta Bean Validation 2.0](https://beanvalidation.org/2.0/), as part of Jakarta EE 8. However, it should also work with Bean Valdation 2.0 (non-Jakarta) and [Bean Validation 1.1](https://beanvalidation.org/1.1/).
