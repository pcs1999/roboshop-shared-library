def compile() {
  if (app_lang == "nodejs") {
    sh 'npm install'
    sh 'env'
  }

  if (app_lang == "maven") {
    sh 'mvn package'
  }

}

def unittests() {

  if (app_lang == "nodejs") {
    // Developer is missing unit test cases in our project, He need to add them as best practice, We are skipping to proceed further
          sh 'npm test || true'

  }

  if (app_lang == "maven") {
    sh 'mvn test'
  }

  if (app_lang == "python") {
    sh 'python3 -m unittest'
  }
}

def email(email_note) {
  mail bcc: '', body: "Job Failed - ${JOB_BASE_NAME}\nJenkins URL - ${JOB_URL}", cc: '', from: 'pcs04031999@gmail.com', replyTo: '', subject: "Jenkins Job Failed - ${JOB_BASE_NAME}", to: 'cp7524420@gmail.com'
}

def artifactPush() {
  sh "echo ${TAG_NAME} >VERSION"

  if (app_lang == "nodejs") {
    sh "zip -r ${component}-${TAG_NAME}.zip node_modules server.js VERSION  ${extrafiles}"
  }
  
  if (app_lang == "nginx" || app_lang == "python" ) {
    sh "zip -r ${component}-${TAG_NAME}.zip * -x jenkinsfile"
  }

  sh 'ls -l'

  NEXUS_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names nexus.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
        NEXUS_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names nexus.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
        wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${NEXUS_PASS}", var: 'SECRET']]]) {
            sh "curl -v -u ${NEXUS_USER}:${NEXUS_PASS} --upload-file ${component}-${TAG_NAME}.zip http://172.31.6.153:8081/repository/${component}/${component}-${TAG_NAME}.zip"
        }

}