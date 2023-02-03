@Library('roboshop') _

pipeline {
    agent any 
    stages {
        stage('test1') {
         steps {
            script {
                def chandra = "firstname"
                def shekar = "lastname"

                print chandra
                print shekar 
            }
         }
        }
    }
}