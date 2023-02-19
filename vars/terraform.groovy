def call () {
    pipeline {
        options {
            ansicolor('xterm')
        }
        agent {
            node {
                label 'work-station'
            }
        
            
        }
    parameters {
        string(name: 'INFRA_ENV', defaultValue: '', description: 'hi hello welcome')
    }

    stages {
       stage ('Terraform Init') {
        steps {
             sh "terraform  init -backend-config=env-${INFRA_ENV}/state.tfvars"
        }
       
       }
    }
    }
}
