[![Spring Hateoas](https://spring.io/badges/spring-hateoas/ga.svg)](http://projects.spring.io/spring-hateoas/#quick-start)
[![Spring Hateoas](https://spring.io/badges/spring-hateoas/snapshot.svg)](http://projects.spring.io/spring-hateoas/#quick-start)
---
tags: [rest, hateoas, hypermedia, security, testing, oauth]
projects: [spring-framework, spring-hateoas, spring-security, spring-security-oauth]
# Reading meter
#  REST service with Spring HATEOAS and  Spring security Oauth

Hypermedia-driven REST service with Spring HATEOAS, a library of APIs that you can use to easily create links pointing to Spring MVC controllers, build up resource representations, and control how they're rendered into supported hypermedia formats such as HAL.
we�re going to build a web application, using jpa to model records in an h2 database.[Web, JPA, H2] 

The service will accept HTTP   requests at:

    http://localhost:8080/connections  GET POST
 
    http://localhost:8080/connections/{connectionId}  GET PUT DELETE
	
    http://localhost:8080/profiles GET POST
	
    http://localhost:8080/profiles/{profileId}  GET  PUT DELETE
	
    http://localhost:8080/consumptions/{consumptionMonth}  GET  
	
    http://localhost:8080/consumptions/{consumptionMonth}/{connectionId}  GET

and respond with a link:/understanding/JSON[JSON] representation of a greeting enriched with the simplest possible hypermedia element, a link pointing to the resource itself:


## Improved Error Handling with `VndErrors`

HATEOAS gives clients improved metadata about the service itself. We can improve the situation for our error handling, as well. HTTP status codes tell the client - broadly - that something went wrong. HTTP status codes from 400-499, for example, tell the client that the client did something wrong. HTTP status codes from 500-599 tell the client that the server did something wrong. If you know your status codes, then this can be a start in understanding how to work with the API. But, we can do better. After all, before a REST client is up and running, somebody needs to develop it, and useful error messages can be invaluable in understanding an API.
Errors that can be handled in a consistent way are even better.
## Client Authentication and Authorization on the Open Web with Spring Security  OAuth 
We can authenticate client requests in a myriad of ways. Clients could send, for example, an HTTP-basic username  and password on each request. They could transmit an x509 certificate on each request. There are indeed numerous approaches that could be used here, with numerous tradeoffs.  

Our API is meant to be consumed over the open-web. It's meant to be used by all manner of HTML5 and native mobile and desktop clients that we intend to build. We shall use  diverse clients with diverse security capabilities, and any solution we pick should be able to accommodate that. We should also decouple the user's username and password from the application's session. 
 The `OAuth2Configuration` configuration class describes one client (here, one for a hypothetical Android client) that needs the `ROLE_USER` and the `write` scope.  Spring Security OAuth will read this information from the `AuthenticationManager` that is ultimately configured using our custom `UserDetailsService` implementation. 
We can explicitly enable XSS for well-known clients by exposing CORS (cross-origin request scripting) headers. These headers, when present in the service responses, signal to the browser that requests of the origin, shape and configuration described in the headers *are* permitted, even across domains.
Using HTTPS (SSL/TLS) to prevent Man-in-the-Middle Attacks:`security/src/main/resources/application-https.properties`. the app is run when *SPRING_PROFILES_ACTIVE* configured with profile *https*.
requires a signed certificate certificate and a certificate password : $ keytool -genkey -alias bookmarks -keyalg RSA -keystore src/main/resources/tomcat.keystore

## Testing a REST ServiceSo,  the following:
 [IN PROGRESS]
Spring MVC provides   support for unit testing HTTP endpoints.

## Resources

-  Reference documentation - [html](http://docs.spring.io/spring-hateoas/docs/current/reference/html/), [pdf](http://docs.spring.io/spring-hateoas/docs/current/reference/pdf/spring-hateoas-reference.pdf)
- [JavaDoc](http://docs.spring.io/spring-hateoas/docs/current-SNAPSHOT/api/)
- [Getting started guide](https://spring.io/guides/gs/rest-hateoas/)
```
 http://localhost:8080/connections/1 GET
{
    "connection": {
        "id": 1,
        "profile": {
            "name": "B"
        },
        "meterReading": [
            {
                "month": "OCT",
                "meterReading": 10
            },
            {
                "month": "APR",
                "meterReading": 4
            },
            {
                "month": "AUG",
                "meterReading": 8
            },
            {
                "month": "MAR",
                "meterReading": 3
            },
            {
                "month": "MAY",
                "meterReading": 5
            },
            {
                "month": "FEB",
                "meterReading": 2
            },
            {
                "month": "NOV",
                "meterReading": 11
            },
            {
                "month": "JUL",
                "meterReading": 7
            },
            {
                "month": "JAN",
                "meterReading": 1
            },
            {
                "month": "JUN",
                "meterReading": 6
            },
            {
                "month": "DEC",
                "meterReading": 12
            },
            {
                "month": "SEP",
                "meterReading": 9
            }
        ],
        "uri": "http://connection.com/1/"
    },
    "_links": {
        "connection-uri": {
            "href": "http://connection.com/1/"
        },
        "connections": {
            "href": "http://localhost:8080/connections"
        },
        "self": {
            "href": "http://localhost:8080/connections/1"
        }
    }
}
```
```
 http://localhost:8080/profiles/1 GET
{
    "_embedded": {
        "profileResourceList": [
            {
                "profile": {
                    "id": 1,
                    "name": "A",
                    "fractions": [
                        {
                            "month": "JUL",
                            "fraction": 0
                        },
                        {
                            "month": "JUN",
                            "fraction": 0
                        },
                        {
                            "month": "MAY",
                            "fraction": 0.1
                        },
                        { http://localhost:8080/profiles/1 GET
                            "month": "JAN",
                            "fraction": 0.1
                        },
                        {
                            "month": "OCT",
                            "fraction": 0.1
                        },
                        {
                            "month": "SEP",
                            "fraction": 0.1
                        },
                        {
                            "month": "APR",
                            "fraction": 0.1
                        },
                        {
                            "month": "AUG",
                            "fraction": 0.1
                        },
                        {
                            "month": "MAR",
                            "fraction": 0.1
                        },
                        {
                            "month": "FEB",
                            "fraction": 0.1
                        },
                        {
                            "month": "DEC",
                            "fraction": 0.1
                        },
                        {
                            "month": "NOV",
                            "fraction": 0.1
                        }
                    ],
                    "uri": "http://profile.com/1/\""
                },
                "_links": {
                    "profile-uri": {
                        "href": "http://profile.com/1/\""
                    },
                    "profiles": {
                        "href": "http://localhost:8080/profiles"
                    },
                    "self": {
                        "href": "http://localhost:8080/profiles/1"
                    }
                }
            }
}      
   ```
   http://localhost:8080/consumptions/JAN GET
  
 {
    "_embedded": {
        "consumptionResourceList": [
            {
                "consumption": {
                    "month": "JAN",
                    "consumption": 1,
                    "uri": "http://connection.com/1//consumptions/JAN"
                },
                "_links": {
                    "consumption-uri": {
                        "href": "http://connection.com/1//consumptions/JAN"
                    },
                    "consumptions": {
                        "href": "http://localhost:8080/consumptions/JAN"
                    },
                    "self": {
                        "href": "http://localhost:8080/consumptions/JAN/1"
                    }
                }
            },
            {
                "consumption": {
                    "month": "JAN",
                    "consumption": 1,
                    "uri": "http://connection.com/1//consumptions/JAN"
                },
                "_links": {
                    "consumption-uri": {
                        "href": "http://connection.com/1//consumptions/JAN"
                    },
                    "consumptions": {
                        "href": "http://localhost:8080/consumptions/JAN"
                    },
                    "self": {
                        "href": "http://localhost:8080/consumptions/JAN/1"
                    }
                }
            }
        ]
    }
}
```
