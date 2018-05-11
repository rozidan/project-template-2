# Project-template
> A spring-boot 2 template project

This s a spring-boot 2 template project consisting of two components:
* Service: A runnable service.
* Client: A client project that can be imported to other services for the purpose of communicating between the Services, contains REST calls and DTO's.

### Service
The service template contains the following:
* Web
* JPA and Flyway examples
* Kafka configuration
* Controllers & services examples
* Spring-boot admin client
* Swagger configuration
* Modelmapper example
* Cloud configuration client
* Devtools and gradle build integration with 'docker image build' task
* Devtools include restart of Modelmapper JAR's
* Audit aware configuration example
* Auditable entity
* `AttributeConverter` example for enum values
* Data unique constrant configuration that automaticlly catch by error handler
* Handler exampleד for the following exceptions:
    * `EmptyResultDataAccessException`
    * `DataIntegrityViolationException`
    * `HttpMessageNotReadableException`
    * `MethodArgumentNotValidException`
    * `HttpRequestMethodNotSupportedException`
    * `HttpMediaTypeNotSupportedException`
    * Global handler example for `Exception`

### Client
The client template contains the following:
* Minimal spring dependencies
* Process resources build configuration
* Swagger dependency for DTO's configuration
* Auditable DTO
* Identifier type interface used to convert enum values to json values
* Error DTO's examples
* Auto configuration of client API
* Auto configuration of `RestTemplate` that can be override

### Available gradle task parameters:
| Task | Property name | Mandatory | Default Value |
|------|---------------|-----------|---------------|
|Maven |signing.keyId|Y|                           |
|Maven |signing.password|Y|                 |
|Maven |signing.secretKeyRingFile|Y|      |
|Maven |mavenRepo|Y|                        |
|Maven |mavenRepoSnapshot|Y|                 |
|Maven |mavenUsername|Y|                     |
|Maven |mavenPassword|Y|                     |
|Docker |devtools|Y|false|
|Docker |dockerUrl|Y|see [bmuschko gradle-docker-plugin](https://github.com/bmuschko/gradle-docker-plugin)|

### Project Name
* Replace all ['template', 'company'] references with your own
*  
