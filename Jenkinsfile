pipeline {
    agent any
    tools {
        jdk 'JDK 17'
        maven 'Maven3'
    }
    environment {
        IMAGE_NAME = 'shoppingcart-app'
        DOCKERHUB_REPO = 'aaronly123/shoppingcart-app'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                echo 'Building project...'
                bat 'mvn clean compile'
            }
        }
        stage('Test & Coverage') {
            steps {
                echo 'Running tests and generating coverage...'
                bat 'mvn test jacoco:report'
            }
            post {
                always {
                    junit '**\\target\\surefire-reports\\*.xml'
                    jacoco execPattern: '**\\target\\jacoco.exec', classPattern: '**\\target\\classes', sourcePattern: '**\\src\\main\\java', inclusionPattern: '**/*.class'
                }
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging application...'
                bat 'mvn package -DskipTests'
            }
        }
        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'
                bat "docker build --no-cache -t %IMAGE_NAME% ."

            }
        }
        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: 'Docker_Hub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker tag %IMAGE_NAME% %DOCKERHUB_REPO%:latest
                        docker push %DOCKERHUB_REPO%:latest
                    """
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning workspace...'
            cleanWs()
        }
    }
}
