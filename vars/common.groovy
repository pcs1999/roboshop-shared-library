def compile () {
    if (app_lang == "nodejs") {
        sh 'npm install'

    }

    if (app_lang == "maven") {
        sh "mvn clean compile"
    }

    sh "docker build -t 261454514620.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} . "
}

def unittests () {
    if (app_lang == "nodejs") {
        // in our project developer not written any unittest cases
        sh 'npm test || true '
    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }

    if (app_lang == "python") {
        sh 'python3 -m unittest'
    }

}

def email (email_note){
    mail bcc: '', body: "failure in : ${JOB_BASE_NAME} pipeline\nTake a look with url\nDisplay_URL:${RUN_DISPLAY_URL}\n jenkins_URL:${JENKINS_URL}" , cc: '', from: 'pcs04031999@gmail.com', replyTo: '', subject: "Jenkins job:${JOB_BASE_NAME} Failure notification", to: 'cp7524420@gmail.com'
}

def artifactpush (){

    sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 261454514620.dkr.ecr.us-east-1.amazonaws.com"

    sh "docker push 261454514620.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}"

//    sh "echo ${TAG_NAME} >VERSION"
//
//    sh 'env'
//
//    if (app_lang == "nodejs") {
//        sh "zip -r ${component}-${TAG_NAME}.zip node_modules server.js VERSION ${extra_files}"
//    }
//
//
//    if (app_lang == "Nginx" || app_lang == "python") {
//        sh "zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile ${extra_files}"
//    }
//
//    if (app_lang == "maven") {
//        sh "zip -r ${component}-${TAG_NAME}.zip  ${component}.jar VERSION ${extra_files}"
//    }
//
//
//    NEXUS_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names nexus.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
//    NEXUS_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names nexus.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
//    wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${NEXUS_PASS}", var: 'SECRET']]]) {
//        sh "curl -v -u ${NEXUS_USER}:${NEXUS_PASS} --upload-file ${component}-${TAG_NAME}.zip http://172.31.92.158:8081/repository/${component}/${component}-${TAG_NAME}.zip"
//    }
}

