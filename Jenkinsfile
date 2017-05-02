library 'common'

node('small'){
    def project="stanford"
    base_build{
        name=project
        test_script = {->
            sh 'echo test'
        }
        release = {->
            ecr.push_image project, base_build.release_version()
            dockerhub.push_image project, base_build.release_version()
        }
        use_cert = true
    }
}
