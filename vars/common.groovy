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
   sh 'npm test'
    // devloper missed the unit test cases in our project, they need to add them for best practice,skipping now moving further
    //sh 'echo test cases'
  }

  if (app_lang == "maven") {
    sh 'mvn test'
  }

  if (app_lang == "python") {
    sh 'python3 -m unittest'
  }
}

def email(email_note) {
mail bcc: '', body: "job failed- ${JOB_BASE_NAME}\nJenkins URL - ${JOB_URL}", cc: '', from: 'cp7524420@gmail.com', replyTo: '', subject: "jenkins job failed - ${JOB_BASE_NAME}", to: 'cp7524420@gmail.com'
}