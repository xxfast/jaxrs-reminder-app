def projectRelease
def branchName
node("java14") {

    def environment = params.env.toLowerCase()

    env.JAVA_HOME = tool "java14"
    env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"


        stage(name: "CLONE") {
            def scmVars = checkout scm
            println("scm vars: ${scmVars}")
            branchName = scmVars.GIT_BRANCH.toString().replace("origin/", "")
        }

        def envVars = load("devops/${environment.toLowerCase()}-env.groovy").env()
        println "EnvVars: ${envVars}"

            stage(name: "BUILD") {
                sh "./mvn clean build -i --parallel"
            }
            stage(name: "DEPLOY") {
                retry(2) {
                	sh "sed -i '/image/s/[0-9]\\+\\.[0-9]\\+\\.[0-9]\\+\$/${version}/' $WORKSPACE/deploy/${environment}/deployment.yaml"
			cat devops/deployment.yaml
                	kubectl apply  -f devops/deployment.yaml --insecure-skip-tls-verify'''
        
        	}
	}
        
}
