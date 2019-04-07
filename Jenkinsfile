node {

    def gradleTool = installGradleIfNotPresent(gradleVersion())

    stage('checkout') {
        sh 'rm -Rf *'
        checkout scm
    }

    stage('build') {
        withEnv(["PATH=${gradleTool}/bin:${env.PATH}"]) {
            sh generateGradleCommand(env.CLEAN)
        }

        junit 'build/test-results/**/*.xml'
    }

}

def generateGradleCommand(String cleanEnv) {
    boolean clean = cleanEnv?.trim() ? cleanEnv.toBoolean() : false
    return clean ? 'gradle clean build' : 'gradle build'
}

def installGradleIfNotPresent(String gradleVersion) {
    def gradleTool = tool name: "gradle-${gradleVersion}", type: 'gradle'
    env.GRADLE_OPTS = '-Xmx2G -Dorg.gradle.daemon=false'
    
    if (!fileExists("${gradleTool}/bin/gradle")) {
        sh "wget -q https://services.gradle.org/distributions/gradle-${gradleVersion}-bin.zip"
        sh "mkdir -p ${gradleTool}"
        sh "unzip -qq -d ${gradleTool}/.. gradle-${gradleVersion}-bin.zip"
    }
    return gradleTool
}

def gradleVersion(){
    '5.2.1'
}