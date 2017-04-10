node("small"){
    stage('prepare'){
        checkout scm
    }
    stage('build'){
        sh 'docker build . -t ${ECR_DOMAIN}/stanford'
    }

    stage('deploy'){
        def GIT_COMMENT = sh(returnStdout: true, script: 'git log --pretty=oneline -1|sed \'s/[^ ]* //\'').trim()

        if(GIT_COMMENT ==~ /release-v[\d.]+/ ){
            docker.withRegistry("https://${ECR_DOMAIN}", "${ECR_KEY}") {
                def RELEASE_VERSION = (GIT_COMMENT =~ /release-(v[\d.]+)/)[0][1]
                sh 'docker tag ${ECR_DOMAIN}/stanford ${ECR_DOMAIN}/stanford:' + RELEASE_VERSION
                sh 'docker push ${ECR_DOMAIN}/${ECR_PROJECT}:word2vec_' + RELEASE_VERSION
            }
        }
    }
}
