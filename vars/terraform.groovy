def call(){
    pipeline {
        agent {
            node {
                label 'workstation'
            }

        }

        parameters{
            string(name:'INFRA_ENV', defaultvalue: '', description: 'enter env like dev or prod')
        }

        stages{
            stage('terraform init'){
                steps{
                    sh "terrafrom init -backend-configuration-${INFRA_ENV}/state.tfvars"
                }
            }
        }
    }
}