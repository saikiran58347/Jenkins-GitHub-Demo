```groovy
pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B -DskipTests clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -B test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B package -DskipTests'
                archiveArtifacts artifacts: 'target/cgpa-calculator.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t cgpa-calculator .'
            }
        }
    }

    post {
        success {
            echo 'CI pipeline and Docker image build completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check the Console Output for details.'
        }
        always {
            echo 'Pipeline run finished.'
        }
    }
}
```