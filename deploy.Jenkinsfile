pipeline {
    agent any

    environment {
        AWS_REGION = "ap-south-1"
        ECR_REPO_URI = "685645913453.dkr.ecr.ap-south-1.amazonaws.com/train-ticket-repo"
    }

    stages {
        stage('Clone Repo') {
            steps {
                git branch: 'main', url: 'https://github.com/HariHaaran3/TrainTicketBookingService.git'
            }
        }
        stage('Build Maven Project') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                bat "docker build -t train-ticket-service ."
            }
        }
        stage('Tag Docker Image') {
            steps {
                bat "docker tag train-ticket-service %ECR_REPO_URI%:latest"
            }
        }
        stage('Login to ECR') {
            steps {
                bat "aws ecr get-login-password --region %AWS_REGION% | docker login --username AWS --password-stdin %ECR_REPO_URI%"
            }
        }
        stage('Push Docker Image') {
            steps {
                bat "docker push %ECR_REPO_URI%:latest"
            }
        }
        stage('Terraform Init') {
            steps {
                dir('infra') {
                    bat 'terraform init'
                }
            }
        }
        stage('Terraform Plan') {
            steps {
                dir('infra') {
                    bat 'terraform plan'
                }
            }
        }
        stage('Terraform Apply') {
            steps {
                dir('infra') {
                    bat 'terraform apply -auto-approve'
                }
            }
        }
    }
}
