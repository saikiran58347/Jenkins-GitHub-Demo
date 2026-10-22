# Jenkins-GitHub-Demo — CGPA Calculator

A small Java Maven project for practicing GitHub + Jenkins Continuous Integration.

## Application rules

- Grade points: S=10, A=9, B=8, C=7, D=6, E=5, F=0
- Accepted credits: 2, 3, or 4
- CGPA = sum(grade point × subject credits) / sum(subject credits)
- An F grade contributes zero points, but the subject's credits remain in the denominator.

## Requirements

- JDK 25 (the `pom.xml` compiler release is set to 25)
- Maven 3.9+
- Git

## Run locally

From the project root:

```bash
mvn clean test
mvn package
java -jar target/cgpa-calculator.jar
```

The JAR is packaged but is not configured as an executable/fat JAR; run the application with Maven instead if `java -jar` reports no main manifest:

```bash
mvn exec:java -Dexec.mainClass=com.demo.App
```

## Jenkins

The root `Jenkinsfile` has these stages: Checkout → Build → Test → Package.
It assumes a Windows Jenkins agent with `mvn` available on PATH and JDK 25 configured.

Create a Jenkins **Pipeline** job named `GitHub-Maven-CI`, choose **Pipeline script from SCM**, select Git, enter this repository's HTTPS clone URL, set branch to `*/main`, and script path to `Jenkinsfile`.

For GitHub webhooks, Jenkins must be reachable from GitHub over the network. `http://localhost:8080/github-webhook/` only works on the local machine and is not reachable by GitHub's servers. If Jenkins is only local, use a secure public tunnel or configure Poll SCM as a fallback.
