# Jenkins-GitHub-Demo — CGPA Calculator

A Java Maven project for practicing GitHub and Jenkins Continuous Integration.

## Application Rules

- Grade points: S = 10, A = 9, B = 8, C = 7, D = 6, E = 5, F = 0
- Accepted credits: 2, 3, or 4
- CGPA = Sum of (Grade Point × Subject Credits) / Sum of Subject Credits
- An F grade contributes zero points, but its credits remain in the denominator.

## Requirements

- JDK 25
- Maven 3.9+
- Git

## Run Locally

```bash
mvn clean test
mvn package
```

To run the application interactively:

```bash
mvn exec:java -Dexec.mainClass=com.demo.App
```

## Jenkins Pipeline

The `Jenkinsfile` contains four stages:

1. Checkout
2. Build
3. Test
4. Package

Create a Jenkins Pipeline job named `GitHub-Maven-CI`, select **Pipeline script from SCM**, choose Git, enter the repository URL, set the branch to `*/main`, and use `Jenkinsfile` as the script path.

For GitHub webhooks, Jenkins must be reachable from GitHub over the network. A localhost URL is not directly accessible to GitHub's servers.
CI/CD pipeline verification
CI/CD pipeline verification
H/2 * * * *
