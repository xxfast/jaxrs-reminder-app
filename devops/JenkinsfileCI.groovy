#!groovy

node("java14") {
    println("${params.env} Iniciando CI")

    env.JAVA_HOME = tool 'java14'
    env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"

        stage(name: "Clone") {
            checkout scm
        }

        stage(name: "Build") {
            try {
                sh "./mvn build --parallel -i"
            } catch(Exception e){
                throw e
            }
        }

        stage(name: "Notify Github") {
            this.notifyGithub('success')
        }


}
