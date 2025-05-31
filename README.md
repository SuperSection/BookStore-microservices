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

### Author

- [Soumo Sarkar](https://www.linkedin.com/in/soumo-sarkar/)

### Local Development Setup

- Install Java 21. Recommended using [SDKMAN](https://sdkman.io/) for Installing JDK
- Choose your preferred IDE: [IntelliJ IDEA](https://www.jetbrains.com/idea/), [VS Code](https://code.visualstudio.com/)
- Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Postman](https://www.postman.com/downloads/) or any REST API Client

### Reference

- [Spring Boot Microservices Complete Tutorial](https://youtu.be/ZKQWwCUEABY?si=W-kK8n5_AP7O-Sei)
