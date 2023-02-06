def call () {
    pipeline{
agent{
     label 'work-station'
    }
stages{ 
 stage('compile/build'){
    steps{
      script{
        common.compile ()
      }
     }
    }
 stage('unit-test'){
    steps{
        echo 'unit-test'
        }
    }
stage('quality-control'){
    steps{
        echo 'quality-control'
        }
    }
stage('upload code to centrailzed palce'){
    steps{
        echo 'upload'
        }
    }


   } 
}
}