def call() {

  if(!env.sonar_extra_opt){
    env.sonar_extra_opt=" "
  }
  try {
    node('workstation') {

      stage('scripted checkout of scm') {
        cleanWs()
        git branch: 'main', url: "https://github.com/pcs1999/${component}.git"
      }

      stage('Compile/Build') {
        common.compile()
      }

      stage('Unit Tests') {
        common.unittests()
      }

      stage('Quality Control') {
        SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
        SONAR_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
        wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
          sh "sonar-scanner -Dsonar.host.url=http://sonarqube.chandupcs.online:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${sonar_extra_opt}"
         //sh "echo sonar scan"
        }


      }

      stage('Upload Code to Centralized Place') {
        echo 'Upload'
      }




    }

  } catch(Exception e) {
    common.email("Failed")
  }
}