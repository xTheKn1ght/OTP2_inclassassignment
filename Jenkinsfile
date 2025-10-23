pipeline {
    agent any
    tools{
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
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Code Coverage') {
            steps {
                echo 'Generating JaCoCo coverage report...'
                bat 'mvn jacoco:report'
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
                bat "docker build -t %IMAGE_NAME% ."
            }
        }
        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
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
