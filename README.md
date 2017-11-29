[![Spring Hateoas](https://spring.io/badges/spring-hateoas/ga.svg)](http://projects.spring.io/spring-hateoas/#quick-start)
[![Spring Hateoas](https://spring.io/badges/spring-hateoas/snapshot.svg)](http://projects.spring.io/spring-hateoas/#quick-start)
---
tags: [rest, hateoas, hypermedia, security, testing, oauth]
projects: [spring-framework, spring-hateoas, spring-security, spring-security-oauth]
= readingmeter
REST service with Spring HATEOAS and 

Hypermedia-driven REST service with Spring HATEOAS, a library of APIs that you can use to easily create links pointing to Spring MVC controllers, build up resource representations, and control how they're rendered into supported hypermedia formats such as HAL.
we’re going to build a web application, using jpa to model records in an h2 database. So, select the following:
Web, JPA, H2, 

The service will accept HTTP   requests at:

    http://localhost:8080/connections
	
    http://localhost:8080/connections/{connectionId}
	
    http://localhost:8080/profiles
	
    http://localhost:8080/profiles/{profileId}
	
    http://localhost:8080/consumptions/{consumptionMonth}
	
    http://localhost:8080/consumptions/{consumptionMonth}/{connectionId}

and respond with a link:/understanding/JSON[JSON] representation of a greeting enriched with the simplest possible hypermedia element, a link pointing to the resource itself:


===  Improved Error Handling with `VndErrors`

HATEOAS gives clients improved metadata about the service itself. We can improve the situation for our error handling, as well. HTTP status codes tell the client - broadly - that something went wrong. HTTP status codes from 400-499, for example, tell the client that the client did something wrong. HTTP status codes from 500-599 tell the client that the server did something wrong. If you know your status codes, then this can be a start in understanding how to work with the API. But, we can do better. After all, before a REST client is up and running, somebody needs to develop it, and useful error messages can be invaluable in understanding an API. Errors that can be handled in a consistent way are even better!

=== Client Authentication and Authorization on the Open Web with Spring Security  OAuth 
We can authenticate client requests in a myriad of ways. Clients could send, for example, an HTTP-basic username  and password on each request. They could transmit an x509 certificate on each request. There are indeed numerous approaches that could be used here, with numerous tradeoffs.  

Our API is meant to be consumed over the open-web. It's meant to be used by all manner of HTML5 and native mobile and desktop clients that we intend to build. We shall use  diverse clients with diverse security capabilities, and any solution we pick should be able to accommodate that. We should also decouple the user's username and password from the application's session. 
 The `OAuth2Configuration` configuration class describes one client (here, one for a hypothetical Android client) that needs the `ROLE_USER` and the `write` scope.  Spring Security OAuth will read this information from the `AuthenticationManager` that is ultimately configured using our custom `UserDetailsService` implementation. 
We can explicitly enable XSS for well-known clients by exposing CORS (cross-origin request scripting) headers. These headers, when present in the service responses, signal to the browser that requests of the origin, shape and configuration described in the headers *are* permitted, even across domains.
Using HTTPS (SSL/TLS) to prevent Man-in-the-Middle Attacks:`security/src/main/resources/application-https.properties`. the app is run when *SPRING_PROFILES_ACTIVE* configured with profile *https*.
requires a signed certificate certificate and a certificate password : $ keytool -genkey -alias bookmarks -keyalg RSA -keystore src/main/resources/tomcat.keystore

===Testing a REST Service
 [IN PROGRESS]
Spring MVC provides   support for unit testing HTTP endpoints.

## Resources

-  Reference documentation - [html](http://docs.spring.io/spring-hateoas/docs/current/reference/html/), [pdf](http://docs.spring.io/spring-hateoas/docs/current/reference/pdf/spring-hateoas-reference.pdf)
- [JavaDoc](http://docs.spring.io/spring-hateoas/docs/current-SNAPSHOT/api/)
- [Getting started guide](https://spring.io/guides/gs/rest-hateoas/)