@Library ('roboshop')

pipeline {
    agent any 
    stages {
        stage('test') {
         steps {
            script {
                def chandra = "first name"
                def shekar = "last name"

                print chandra
                print shekar 
            }
         }
        }
    }
}