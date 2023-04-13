def call() {

  pipeline {

    agent {

      label 'workstation'

    }

    stages {

      stage('compile/build') {

        steps {

          echo 'compile'
        }
      }


      stage('unit/test') {

        steps {

          echo 'unit-test'
        }
      }

      stage('quality controll') {

        steps {

          echo 'quality controll'
        }
      }

    }


    stage('upload code to centralized place') {

      steps {

        echo 'upload code to centralized place'
      }
    }

  }


 }



