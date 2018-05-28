### What is this?

This is a demo application that serves as an _executable guideline_ for migrating a large-ish service written in "Play for Java" framework to a traditional servlet-based Spring MVC service.

### Components

#### Domain

- [CompanyGuardian](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/company/CompanyGuardian.java) aspect guarding "companyId" request header 
- [CustomerEntity](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/customer/CustomerEntity.java) entity representing a simple "customer" record (DTO omitted for brevity)
- [CustomerRepository](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/customer/CustomerRepository.java) - for manipulating `CustomerEntity`
- [CustomerQueries](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/customer/CustomerQueries.java) and [CustomerCommands](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/customer/CustomerCommands.java) - service-layer components, logical transaction boundaries
- [CustomerReadingController](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/customer/CustomerReadingController.java) and [CustomerWritingController](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/customer/CustomerWritingController.java) - Spring MVC controllers using _queries, commands, and preconditions_

#### Non-Functional

- [DbContextHolder](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/data/DbContextHolder.java) - keeps track (thread local) about which database to use (master/slave replication)
- [RoutingDataSourceTransactionInterceptor](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/data/RoutingDataSourceTransactionInterceptor.java) - automatically switches master/slave data source based on the logical transaction type (reading/writing)  
- [PreconditionsAction](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/precondition/PreconditionsAction.java) - invokes all provided preconditions before proceeding/rejecting the main logic call (delegated by the controller)
- [ValidPayloadPrecondition](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/precondition/ValidPayloadPrecondition.java) - payload-aware precondition that binds the JSON request, and executes any required validations
- [RequestLoggingFilter](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/web/logging/RequestLoggingFilter.java) - logs HTTP requests
- [ErrorHandler](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/web/ErrorHandler.java) - handles common errors (400s, 5xx)
- [SpringHttpContext](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/web/SpringHttpContext.java) - adapter between Play's `ctx()` and Spring/Servlet world

### Un-Spring way

Would we write an app like this had we started with Spring MVC straight away? No. But we have a bunch of existing components and we need to mitigate the risks of this migration as much as we can. Therefore we're carrying over some styles/components that are not typically seen in Spring MVC applications:

- Guardians instead of Controller Advices
    - manual aspects guarding path variables and query parameters
- Preconditions instead of Controller Advices and/or auto-binding
    - `@ModelAttribute`-like advices are substituted with `PreconditionsAction`
- Manual validation instead of `@Valid`
    - `@Valid` and custom validators are _possible_ (and possibly preferred for the future usages), but this demo also supports manual payload preconditions and [JSON request to POJO binding](https://github.com/vrto/playaway/blob/1ee2f5c3a4b8958934a8618a258ddcdabf9336e2/src/main/java/net/helpscout/playaway/precondition/ValidPayloadPrecondition.java#L79)

### Play-only concepts and their replacements

- Actions `@With(FooAction.class)` are replaced with Controller Advices, see [ErrorHandler](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/web/ErrorHandler.java) for example
- `application.conf` is replaced with [application.yml](https://github.com/vrto/playaway/blob/master/src/main/resources/application.yml)
- `ctx()` (hidden behind [HttpContext](https://github.com/vrto/playaway/blob/master/src/main/java/net/helpscout/playaway/web/HttpContext.java)) are replaced by Spring-first binding (`@RequestHeader`, `@RequestParam` etc.) or adapted using `RequestContextHolder` (for storing request-only data)