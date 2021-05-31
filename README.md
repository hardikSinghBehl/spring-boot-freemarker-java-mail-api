# Hedwig
#### Proof-of-concept application to send emails to user on event occurence

[Click Here To Use Running Application](https://java-mail-freemarker-spring.herokuapp.com/hedwig/swagger-ui.html)

## Technologies Used
* Java Spring Boot
* JavaMail-API
* Spring Events
* Apache Freemarker
* JPA/H2 in-memory database
* Lombok
* Open-API (swagger-ui) 
* [Bootstrap-Email Editor](https://app.bootstrapemail.com/editor)

## Spring Events
* [Reference Article](https://www.baeldung.com/spring-events)
* Event classes will represent an event for which a mail has to be sent (account-creation-event in this context can be anything depending on business logic)
* A listener class that listens to the above created event and executes code whenever the above event occures
* A publisher will publish the event (notify that event has occurred) and can be injected in the service layer
* Listener method to be executed asynchronously (recommended)

## Main classes (Drag to new window)
* [Email-Service](https://github.com/hardikSinghBehl/spring-boot-freemarker-java-mail-api/blob/main/src/main/java/com/hardik/hedwig/mail/service/EmailService.java) (to send emails to provided email-id with provided template and subject)
* [Account Creation Event](https://github.com/hardikSinghBehl/spring-boot-freemarker-java-mail-api/blob/main/src/main/java/com/hardik/hedwig/mail/event/UserAccountCreationEvent.java)
* [Account Creation Event Listener](https://github.com/hardikSinghBehl/spring-boot-freemarker-java-mail-api/blob/main/src/main/java/com/hardik/hedwig/mail/listener/UserAccountCreationListener.java) (receives userCreationRequestDto as input and sends mail to provided email-id asynchronously)
* [User Service](https://github.com/hardikSinghBehl/spring-boot-freemarker-java-mail-api/blob/main/src/main/java/com/hardik/hedwig/service/UserService.java) (class that creates user account in the system and publishes that AccountCreationEvent has occurred)

## Bootstrap Email

* HTML for emails is slightly different from HTML for web
* Copy the HTML generated from bootstrap email editor (right part of screen) to a .ftl file under **src/main/resources/templates** (configured in FreemarkerConfiguration.class)
* **account-creation-success.ftl** in templates folder will be generated to the below image
* ${emailId} and ${fullName} will be converted to provided emailId and fullname with the help of apache freemarker

![mail](https://user-images.githubusercontent.com/69693621/120240895-4fc6dd00-c27f-11eb-9fc5-ef7da9144d5d.png)

## Local Setup

* Install Java 15
* Install Maven

Recommended way is to use [sdkman](https://sdkman.io/) for installing both maven and java

Go to application.properties and configure your email and [app password](https://devanswers.co/create-application-specific-password-gmail/) (diffrent from regular passwords)

```
spring.mail.username = <EMAIL-ID-HERE>
spring.mail.password = <PASSWORD-HERE>
```

Run the below commands in the core

```
mvn clean install
```

```
mvn spring-boot:run
```

server port is configured to 9090 and base-url to /hedwig which can be changed in application.properties file

Go to the below url to view swagger-ui (API docs)

```
http://localhost:9090/hedwig/swagger-ui.html
```

Run Tests

```
mvn test
```