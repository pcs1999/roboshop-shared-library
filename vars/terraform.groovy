def call () {
    pipeline {
        options {
            ansiColor('xterm')
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

       stage ('Terraform plan') {
        steps {
             sh "terraform  plan -auto-approve -var-file=env-${INFRA_ENV}/main.tfvars"
        }
       
       }
       post {
        always{
            cleanWs()
        }
       }
    }
    }
}
