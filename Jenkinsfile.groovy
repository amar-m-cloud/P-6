pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/yourusername/your-repo.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.yourdomain.com', 'docker-credentials') {
                        def imageName = "your-image-name"
                        def imageTag = "${env.BUILD_NUMBER}"

                        def dockerImage = docker.build("${imageName}:${imageTag}", '.')

                        dockerImage.push()
                    }
                }
            }
        }

        stage('Security Scan') {
            steps {
                script {
                    sh "trivy image ${imageName}:${imageTag}"
                }
            }
        }
    }
}
