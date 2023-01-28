# ip-validation
[![Maven Central](https://img.shields.io/maven-central/v/com.github.robtimus/ip-validation?versionPrefix=1)](https://search.maven.org/artifact/com.github.robtimus/ip-validation)
[![Build](https://github.com/robtimus/ip-validation/actions/workflows/build.yml/badge.svg?branch=1.x)](https://github.com/robtimus/ip-validation/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.robtimus%3Aip-validation&branch=1.x&metric=alert_status)](https://sonarcloud.io/summary/overall?id=com.github.robtimus%3Aip-validation&branch=1.x)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.robtimus%3Aip-validation&branch=1.x&metric=coverage)](https://sonarcloud.io/summary/overall?id=com.github.robtimus%3Aip-validation&branch=1.x)
[![Known Vulnerabilities](https://snyk.io/test/github/robtimus/ip-validation/1.x/badge.svg)](https://snyk.io/test/github/robtimus/ip-validation/1.x)

Provides validation constraints that work on both [CharSequence](https://docs.oracle.com/javase/8/docs/api/java/lang/CharSequence.html) and [IPAddress](https://robtimus.github.io/ip-utils/apidocs/com/github/robtimus/net/ip/IPAddress.html). These not only allow you to validate that a value is a valid IP address, but also that the IP address is contained in a specific IP range or subnet.

## Bean Validation API support

This library has been written for [Jakarta Bean Validation 2.0](https://beanvalidation.org/2.0/), as part of Jakarta EE 8. However, it should also work with Bean Valdation 2.0 (non-Jakarta) and [Bean Validation 1.1](https://beanvalidation.org/1.1/). The [jakarta.validation:jakarta.validation-api](https://search.maven.org/search?q=g:jakarta.validation%20AND%20a:jakarta.validation-api) dependency is a provided dependency, allowing you to provide your own version without any conflicts.

Note that this library does **not** support Bean Validation as part of Jakarta EE 9, as that uses a different package prefix (`jakarta` instead of `javax`). This means that versions 3.0.0 and up of the [jakarta.validation:jakarta.validation-api](https://search.maven.org/search?q=g:jakarta.validation%20AND%20a:jakarta.validation-api) dependency are not supported.
