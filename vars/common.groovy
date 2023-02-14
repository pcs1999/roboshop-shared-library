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

def artifactpush () {
  if (app_lang == "nodejs") {
    sh "zip -r cart-${TAG_NAME}.zip node_modules server.js"
  }
  sh 'ls-l'
}