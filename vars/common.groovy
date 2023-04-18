def compile () {
    if (app_lang == "nodejs") {
        sh 'npm install'
        sh 'env'
    }

    if (app_lang == "maven") {
        sh 'mvn package'
    }
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

def artifactpush (){
    if (app_lang == "nodejs") {
        sh "zip -r cart-${TAG_NAME}.zip node_modules server.js"
    }
}
def email (email_note){
    mail bcc: '', body: "failure in : ${JOB_BASE_NAME} pipeline\nTake a look with url\nDisplay_URL:${RUN_DISPLAY_URL}\n jenkins_URL:${JENKINS_URL}" , cc: '', from: 'pcs04031999@gmail.com', replyTo: '', subject: "Jenkins job:${JOB_BASE_NAME} Failure notification", to: 'cp7524420@gmail.com'
}