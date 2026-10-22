# Setup checklist (follow one step at a time)

## 1. Extract and test

1. Extract `Jenkins-GitHub-Demo.zip`.
2. Open the extracted `Jenkins-GitHub-Demo` folder in VS Code.
3. Open VS Code Terminal → New Terminal.
4. Run `mvn clean test`.
5. Expect `BUILD SUCCESS` and 7 tests with 0 failures.

## 2. Create the GitHub repository

1. On GitHub, create a repository named `Jenkins-GitHub-Demo`.
2. For easiest first push, create it as an empty repository (do not add README, license, or `.gitignore`).
3. In VS Code terminal, make sure you are in the extracted project root and run:

```bat
git init
git branch -M main
git add .
git commit -m "Add Maven CGPA calculator and tests"
git remote add origin https://github.com/YOUR-USERNAME/Jenkins-GitHub-Demo.git
git push -u origin main
```

Replace `YOUR-USERNAME` with your GitHub username. If `origin` already exists, use `git remote set-url origin https://github.com/YOUR-USERNAME/Jenkins-GitHub-Demo.git` instead of `git remote add origin ...`.

## 3. Create Jenkins pipeline job

1. Open `http://localhost:8080`.
2. Select **New Item**.
3. Enter `GitHub-Maven-CI`.
4. Choose **Pipeline**, then **OK**.
5. Under **Pipeline**, choose **Pipeline script from SCM**.
6. SCM: **Git**.
7. Repository URL: `https://github.com/YOUR-USERNAME/Jenkins-GitHub-Demo.git`.
8. Branch specifier: `*/main`.
9. Script path: `Jenkinsfile`.
10. Save, then click **Build Now**.
11. Open the build and check **Console Output**, stage results, test report, and archived `cgpa-calculator.jar`.

## 4. If Jenkins says `mvn` is not recognized

Jenkins may run as a Windows service with a different PATH. Configure Maven/JDK in **Manage Jenkins → Tools** if those options are available, or add the Maven `bin` and JDK `bin` paths to the service environment and restart Jenkins. The project targets Java 25, so the Jenkins agent needs JDK 25.

## 5. GitHub webhook

In GitHub repository → **Settings → Webhooks → Add webhook**:
- Payload URL: `https://YOUR-PUBLIC-JENKINS-HOST/github-webhook/`
- Content type: `application/json`
- Select **Just the push event**.

Do not enter `http://localhost:8080/github-webhook/` as the GitHub payload URL: GitHub cannot reach your computer's localhost. If Jenkins is only available locally, use a secure tunnel or configure Poll SCM in the Jenkins job as a fallback.
