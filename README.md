# Introduction

This document presents the main elements of the project SkyJo.

## Technologies

The following tools are needed for this project:

| Techno     | Version          |
| ---------- | ---------------- |
| JAVA       | 17.0.2 minimum   |

## Project setup 

### API

We assume you have a JDK installation on your system. We also assume you have set the JAVA_HOME environment variable pointing to your JDK installation (ex. C:\Program Files\Java\jdk-17). Otherwise you can download JAVA JDK from here: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

**<ins>Intall Maven</ins>**

To install maven in your system, dowload it from here: https://maven.apache.org/download.cgi, then sdd the bin directory of the created directory **apache-maven-3.9.6** to the PATH environment variable (ex C:\apache-maven-3.9.6\bin)

Confirm with the following command in a new shell: 

```bash
mvn -v
```

**<ins>Compile, build and run the application</ins>**

Run the following command to compile the project:

```bash
mvn compile
```

This command will download all required dependecies, and compile the project. You can now build your project:

```bash
mvn package
```

This command will create your **.tar** executable object in the **target** folder, and test your external connexions. Once your delivrable is OK, you can run the application:

```bash
java -jar target/<maven-object-name>.jar
```

**<ins>Data Access configuration</ins>**

This project uses JPA framework for data access. We need then to add the following configurations to the **application.properties** file:

```bash
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.SQLServerDialect
spring.jpa.hibernate.ddl-auto= update
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

**<ins>CORS configuration</ins>**

A front-end project has been developed as part of the delivery package. We must add the following configuration to the **application.properties** file so the API can accepts request from the front-end server:

```bash
cors.white-list= <local address>
```