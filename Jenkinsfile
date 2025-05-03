pipeline {
    agent any
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
                bat 'docker build -t train-ticket-service .'
            }
        }
        stage('Stop Existing Container') {
            steps {
                bat 'docker rm -f ticket-app || exit 0'
            }
        }
        stage('Run Docker Container') {
            steps {
                bat 'docker run -d -p 9090:9090 --name ticket-app train-ticket-service'
            }
        }
    }
}
