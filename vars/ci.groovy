def call () {
    try {
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
 stage('unit Test'){
    steps{
        script{
        common.unittests ()
      }
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
}} catch (Exception e){
    common.email("failed")
}
}