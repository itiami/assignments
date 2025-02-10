# Java Project for Duke University Assignment

## Author
**ABDULLAH AL Numan**

## Project Overview
This is a Java project developed as part of an assignment for Duke University.

## Build Method
The project is built using **Gradle** with DSL **Groovy**.

---

## IntelliJ Configuration
To configure IntelliJ IDEA for this project, follow these settings:

### Gradle Configuration
1. Navigate to: **Settings > Build, Execution, Deployment > Gradle**
2. Set the following options:
    - **Build and run using**: Gradle
    - **Run tests using**: Gradle
    - **Use Gradle From**: Specified Location - `C:/SDK/Gradle_SDK/gradle-8.12.1`
    - **Gradle JVM**: Oracle OpenJDK version `23.02`

### Project Structure
1. **Project SDK**: Oracle OpenJDK version `23.02`
2. **Language Level**: 19 - No new language features

### Run Configuration
1. Go to: **Run > Edit Configuration**
2. Select **Build and Run Java 23**

---

## Gradle Configuration

### `gradle-wrapper.properties`
```
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.12.1-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
org.gradle.logging.level=INFO
```

### `settings.gradle`
```
rootProject.name = 'Java_Duke_University'
```

### `build.gradle`
```groovy
plugins {
    id 'application'
}

group 'co.wali'
version '1.0-SNAPSHOT'
apply plugin: 'application'

application {
    // Define the main class for the application.
    mainClass = 'co.wali.Main'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("libs/courserajava.jar"))
//    implementation(files("libs/apache-csv.jar"))
    implementation 'org.apache.commons:commons-csv:1.13.0'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
    implementation 'org.jsoup:jsoup:1.18.1'
}

test {
    useJUnitPlatform()
}
```

---

## How to Build and Run
### Build the project:
```sh
gradle check
```

### Run the project:
```sh
gradle run
```

### Run Tests:
```sh
gradle test
```

---

## License
This project is developed for educational purposes as part of a Duke University assignment. Redistribution and modification are subject to course guidelines.

