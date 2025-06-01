# BookStore Microservice Application

![BookStore Microservices](./diagram/bookstore-microservices.png)

## What is Microservice Architecture?

Microservices is an architecture style where large applications are composed of small services that provide specific business capabilities that can be deployed and managed independently.

### Pros of Microservices

- Easy to scale individual services as per requirement
- Easy to adopt newer technologies if needed
- Smaller codebases easy to understand & maintain
- Easier to migrate code of a service from one language to another
- Less dependency on other team deliverables

### Cons of Microservices

- Difficult to build & manage distributed systems
- Difficult to test & debug
- Complex deployment process
- Performance issues (e.g., high latency can cause slower response time)

## Monolithic Architecture

Monolithic architecture is a type of application design where all components, including the user interface, business logic, and data access layers, are tightly integrated into a single, inseparable unit. This means the entire application is developed, deployed, and managed as a single package.

### Pros of Monolithic Architecture

- Simpler Development
- Easier Testing & Debugging
- Simpler Deployment

### Cons of Monolithic Architecture

- Difficult to scale sub-systems (modules)
- Difficult to adopt new technologies
- Higher chance to become "big ball of mud" – messy codebase & complex service communications

---

## Create Root Maven Project

1. Create a root Maven project with the following Maven coordinates:

    ```yml
    groupId: com.supersection
    artifactId: bookstore-microservice-application
    version: 0.0.1-SNAPSHOT
    type: pom
    ```

2. Create `.sdkmanrc` file with Java and Maven version configured

   ```yml
   java=21.0.7-tem
   maven=3.9.6
   ```

   This file allows SDKMAN to automatically switch to the specified versions when you enter the project directory.

### SDKMAN Command Reference

```bash
# List all available versions of Java
sdk list java

# Install a specific Java version
sdk install java 21.0.7-tem

# Install a specific Maven version
sdk install maven 3.9.6

# Apply the versions specified in .sdkmanrc
sdk env

# Automatically apply .sdkmanrc when changing directories (if enabled)
sdk env install
```

### Enable Auto-Environment Switching

To allow SDKMAN to automatically switch SDK versions based on the `.sdkmanrc` file:

1. View the current SDKMAN configuration:

   ```bash
   cat ~/.sdkman/etc/config
   ```

2. Modify the config by setting:

   ```ini
   sdkman_auto_env=true
   ```

   This enables SDKMAN to automatically detect and use the specified SDK versions whenever you cd into a directory containing a .sdkmanrc file.

#### Final Check

After making changes, restart your terminal and navigate to your project directory:

```bash
cd /path/to/your/project
```

You should see SDKMAN automatically switching to the configured Java and Maven versions.

### Setup Maven Wrapper (`mvnw`)

The Maven Wrapper is a small script and supporting files added to your project that ensure a specific Maven version is used — **without requiring it to be installed globally**.

Install Maven Wrapper using:

```bash
mvn wrapper:wrapper
```

---

## Catalog-service 

Create a new Spring Boot microservice with the name “catalog-service” and add the following features:

- Add dependencies:
   - Web
   - Validation
   - Spring Data JPA
   - Flyway
   - PostgreSQL
   - Actuator
   - Prometheus
   - DevTools
   - Configuration Processor
   - Testcontainers

- Add springdoc-openapi dependency
- Add rest-assured dependency
- Add git-commit-id-maven-plugin
- Add spotless-maven-plugin
- Create docker-compose file for PostgreSQL
- Add GitHub Action workflow

---

## Application Monitoring with Spring Boot Actuator

Spring Boot Actuator provides production-ready features to monitor and manage applications. It exposes a wide range of endpoints (like `/actuator/health`, `/actuator/info`, `/actuator/metrics`) which help in observing system health, build data, and runtime environment.

### Actuator Setup

1. Dependency (Spring Boot Starter):

    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```

2. Set catalog-service server port on `8081`
3. Access Actuator endpoints in browser: <http://localhost:8081/actuator>
4. Expose all actuator endpoints (Add the following in `application.properties`):

    ```properties
    management.endpoints.web.exposure.include=*
    ```

### Show Build Info

`pom.xml` build Configuration:

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <executions>
    <execution>
      <goals>
        <goal>build-info</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

This will add build metadata like version, group, and timestamp, which also appears in `/actuator/info`.

### Show Git Commit Info in `/actuator/info`

The `git-commit-id-maven-plugin` extracts Git metadata and generates `git.properties` under `target/classes`.

1. Plugin Setup in `pom.xml`:

    ```xml
    <plugin>
      <groupId>io.github.git-commit-id</groupId>
      <artifactId>git-commit-id-maven-plugin</artifactId>
      <executions>
        <execution>
          <goals>
            <goal>revision</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <failOnNoGitDirectory>false</failOnNoGitDirectory>
        <failOnUnableToExtractRepoInfo>false</failOnUnableToExtractRepoInfo>
        <generateGitPropertiesFile>true</generateGitPropertiesFile>
        <includeOnlyProperties>
          <includeOnlyProperty>^git.branch$</includeOnlyProperty>
          <includeOnlyProperty>^git.commit.id.abbrev$</includeOnlyProperty>
          <includeOnlyProperty>^git.commit.user.name$</includeOnlyProperty>
          <includeOnlyProperty>^git.commit.message.full$</includeOnlyProperty>
        </includeOnlyProperties>
      </configuration>
    </plugin>
   ```

2. Once configured, build the app:

    ```bash
    mvn clean package
    ```

3. Enable full Git metadata display in `/actuator/info`

    ```properties
    management.info.git.mode=full
    ```

Access Git metadata at: <http://localhost:8081/actuator/info>

---

## Setup Swagger API Documentation

For the integration between spring-boot and swagger-ui, add the dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.8</version>
</dependency>
```

- The Swagger UI page will then be available at `http://server:port/context-path/swagger-ui.html`
- The OpenAPI description will be available in json format at: `http://server:port/context-path/v3/api-docs`

Refer to the Official [springdoc-openapi]((https://springdoc.org/)) page

---

## Integration Testing with Rest-Assured

[Rest-Assured](https://github.com/rest-assured/rest-assured) is a Java DSL for testing RESTful APIs. It simplifies writing expressive and maintainable API tests with fluent syntax.

Add the following dependency to your `pom.xml`:

```xml
<dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>5.5.5</version>
      <scope>test</scope>
</dependency>
```

Notes:

1. You should place rest-assured before the JUnit dependency declaration in your `pom.xml` / `build.gradle` in order to make sure that the correct version of Hamcrest is used. 
2. REST Assured includes JsonPath and XmlPath as transitive dependencies

---

## Java Code Formatter

Add the following plugin to your `pom.xml` (in each service):

```xml
<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>2.44.5</version>
    <configuration>
        <java>
            <importOrder />
            <removeUnusedImports />
            <palantirJavaFormat>
                <version>2.35.0</version>
            </palantirJavaFormat>
            <formatAnnotations />
        </java>
    </configuration>
    <executions>
        <execution>
            <phase>compile</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Run the following command to apply consistent code formatting:

```bash
./mvnw spotless:apply
```

> **NOTE**: To run the command from the Root project directory, you need to add the same plugin in the root `pom.xml` build config.

---

## Docker Compose Setup

### Infrastructure & Dev Tools (`infra.yml`)

We are defining all supporting services & dev tools under the `deployment/docker-compose/infra.yml` file. They are needed to run or test the application locally. It includes:

- `catalog-db` → PostgreSQL image: `postgres:17-alpine3.21` (primary RDBMS)

Spin up the services under this file:

```bash
docker compose -f ./deployment/docker-compose/infra.yml up -d
```

---

## GitHub Actions CI Setup

This project uses GitHub Actions for automated Continuous Integration.

- Runs on every push and pull request
- Setup Java 21 with Maven caching
- Lints code and checks formatting
- Build the project with Maven

CI configuration for each service is defined under `.github/workflows/`.

> This ensures every change is validated early and consistently across environments.

---

## Taskfile Setup

1. Refer to the [Official Page](https://taskfile.dev/installation/) for installing task locally on your machine.

2. Check if task is installed correctly:

    ```bash
    task --version
    ```

3. Define the `Taskfile.yml` in the root project directory.
4. Afterward you can simply run specific `task` command to handle various project related commands in a consistent way.

### Example usage of `task` command

- To build projects while keeping the code properly formatted beforehand:
    
    ```bash
    task
    ```

- To start infrastructure service containers (`infra.yml`)

    ```bash
    task start_infra
    ```

---

### Author

- [Soumo Sarkar](https://www.linkedin.com/in/soumo-sarkar/)

### Local Development Setup

- Install Java 21. Recommended using [SDKMAN](https://sdkman.io/) for Installing JDK
- Choose your preferred IDE: [IntelliJ IDEA](https://www.jetbrains.com/idea/), [VS Code](https://code.visualstudio.com/)
- Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Postman](https://www.postman.com/downloads/) or any REST API Client

### Reference

- [Spring Boot Microservices Complete Tutorial](https://youtu.be/ZKQWwCUEABY?si=W-kK8n5_AP7O-Sei)
