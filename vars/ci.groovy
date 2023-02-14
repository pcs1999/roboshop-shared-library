def call() {

  if(!env.java_opt) {
    env.java_opt = " "
  }
 
 if (!env.TAG_NAME){
     env.PUSH_CODE = 'false' 
 } else {
  env.PUSH_CODE = 'true'
 }

  try {
    node('work-station') {

      stage('Checkout') {
        cleanWs()
        git branch: 'main', url: "https://github.com/pcs1999/${component}.git"
        sh 'env'
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
          sh "sonar-scanner -Dsonar.host.url=http://172.31.12.42:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${java_opt}"
        }
      }
      if (env.PUSH_CODE == 'true') {
      stage('Upload Code to Centralized Place') {
        common.artifactPush()
      } 
      }
      

    }

  } catch(Exception e) {
    common.email("Failed")
  }
}