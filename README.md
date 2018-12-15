# Loan service 

Loan service with Rest API written in Java

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need following tools:
 - jdk 1.8 
 - Apache Maven 3.3.9

### Installing

To build this app

```
mvn package
```

To run it locally
```
java -jar ./target/loan-<version>.jar --server.port=8080
```
You can override any property as showed above  with the port

Following API endpoints are available:
```
POST /loans - create  loan resource
PUT /loans - update  loan resource  with fixed term days
```
## Running the tests

Running junit tests
```
mvn test
```

Running integration tests

```
mvn integration-test
```
## Coding style

Coding style is defined in .editorconfig file in  project root

```
[*]
charset=utf-8
end_of_line=lf
insert_final_newline=false
indent_style=space
indent_size=4
```

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/wkoszycki/loan/tags). 

## Authors

* **Wojciech Koszycki** - *Initial work* - [wkoszycki](https://github.com/wkoszycki)

See also the list of [contributors](https://github.com/wkoszycki/loan/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
