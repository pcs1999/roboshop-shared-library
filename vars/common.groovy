def compile () {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }

    if (app_lang == "maven") {
        sh 'mvn package'
    }
}

def unittests () {
    if (app_lang == "nodejs") {
        // in our project developer not written any unittest cases
        sh 'npm test '
    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }

    if (app_lang == "python") {
        sh 'python3 -m unittest'
    }

}

def email (email_note){
    mail bcc: '', body: 'failure occurred ', cc: '', from: 'pcs04031999@gmail.com', replyTo: '', subject: 'Jenkins Failure notification', to: 'cp7524420@gmail.com'
}