def call() {

  pipeline {

    agent {

      label 'workstation'

    }

    stages {

      stage('compile/build') {

        steps {
          script {
            common.compile()
          }
        }
      }


      stage('unit/test') {

        steps {

          script {
            common.unittests()
          }
        }
      }

      stage('quality control') {

        steps {

          echo 'quality control'
        }
      }

      stage('upload code to centralized place') {

        steps {

          echo 'upload code to centralized place'
        }
      }

    }



  }

}






