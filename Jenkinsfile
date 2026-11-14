pipeline {
    agent any

    // Automatic CI trigger: poll SCM every minute for changes
    triggers {
        pollSCM('* * * * *')
    }

    environment {
        // Build versioning enhancement: 1.0.<BUILD_NUMBER>
        APP_VERSION = "1.0.${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/nandish123-tech/Employee-Directory-DevOps.git'
                script {
                    if (isUnix()) {
                        sh 'git log -1 --oneline'
                    } else {
                        bat 'git log -1 --oneline'
                    }
                }
            }
        }

        // CI Enhancement: Automated unit & integration testing before build
        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'chmod +x mvnw'
                        sh './mvnw -B test'
                    } else {
                        bat 'mvnw.cmd -B test'
                    }
                }
            }
            post {
                always {
                    // Publish JUnit XML test results for test trend reporting
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh "./mvnw -B -DskipTests -Drevision=${APP_VERSION} clean package"
                    } else {
                        bat "mvnw.cmd -B -DskipTests -Drevision=${APP_VERSION} clean package"
                    }
                }
            }
        }
    }

    // Post-build action: archive the generated versioned artifact
    post {
        success {
            archiveArtifacts artifacts: 'target/employee-directory-*.jar', fingerprint: true
            echo "Successfully packaged and archived employee-directory-${APP_VERSION}.jar"
        }
        failure {
            echo "Pipeline failed during build or test execution."
        }
    }
}
