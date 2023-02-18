def call () {
    pipeline {
        agent {
            node {
                label = 'work-station'
            }
        
            
        }
    parameters {
        string(name: 'INFRA_ENV', defaultValue: '', decryption: 'hi hello welcome')
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
