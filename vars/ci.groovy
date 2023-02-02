def call () {
    pipeline{
agent{
     lablel 'workstation'
    }
stages{ 
 stage('compile/build'){
    steps{
      ehco 'compile'
        }
    }
 stage('unit-test'){
    steps{
        ehco 'unit-test'
        }
    }
stage('quality-control'){
    steps{
        ehco 'quality-control'
        }
    }
stage('upload code to centrailzed palce'){
    steps{
        ehco 'upload'
        }
    }


   } 
}
}