pipeline {
    agent any
    environment {
        AWS_REGION = 'ap-south-1'
    }
    stages {
        stage('Clone Repo') {
            steps {
                git branch: 'main', url: 'https://github.com/HariHaaran3/TrainTicketBookingService.git'
            }
        }
        stage('Terraform Init & Apply (infra setup + ECR creation)') {
            steps {
                dir('infra') {
                    bat 'terraform init'
                    bat 'terraform apply -auto-approve'
                }
            }
        }
        stage('Read ECR Repo URI') {
            steps {
                script {
                    def output = bat(script: 'terraform -chdir=infra output -raw ecr_repo_uri', returnStdout: true).trim()
                    env.ECR_REPO_URI = output
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                bat "docker build -t train-ticket-service ."
            }
        }
        stage('Tag Docker Image') {
            steps {  // ✅ FIX: added steps block
                script {
                    def ecrRepoUri = bat(script: 'terraform -chdir=infra output -raw ecr_repo_uri', returnStdout: true).trim()
                    bat "docker tag train-ticket-service ${ecrRepoUri}:latest"
                }
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
        stage('Terraform Deploy ECS Service') {
            steps {
                dir('infra') {
                    bat 'terraform apply -auto-approve'
                }
            }
        }
    }
}
