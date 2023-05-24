def  call(){
  pipeline {
    agent any
    parameters{
          string(name:'APP_ENV', defaultValue: '', description: 'enter app_env like  dev or prod')
          string(name:'COMPONENT', defaultValue: '', description: 'enter which component')
          string(name:'APP_VERSION', defaultValue: '', description: 'enter APPLICATION version to deploy ')



//      choice(name: 'INFRA_ENV', choices: ['dev', 'prod'], description: 'enter env like dev or prod')
//      choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Pick something')
    }

      options {
          ansiColor('xterm')
      }

      environment {
          SSH=credentials('SSH')
      }



    stages {
      stage ('Run deployment') {
        steps {
          sh '''
            aws ssm put-parameter --name "${APP_ENV}.${COMPONENT}.app_version" --type "String" --value "${APP_VERSION}" --overwrite
            aws ec2 describe-instances     --filters "Name=tag:Name,Values=${APP_ENV}-${COMPONENT}"  | jq ".Reservations[].Instances[].PrivateIpAddress" >/tmp/hosts
            ansible-playbook -e host=localhost -u /tmp/hosts deploy.yml -e component=${COMPONENT} -e env=${APP_ENV} -e ansible_user=${SSH_USR} -e ansible_password=${SSH_PSW}       '''
        }
      }
    }
  }
}