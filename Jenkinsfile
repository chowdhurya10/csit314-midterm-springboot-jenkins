pipeline {
    agent any
    options {
        timestamps()
        disableConcurrentBuilds()
        skipDefaultCheckout(true)
        timeout(time: 15, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Test') {
            steps { sh 'mvn -B -ntp clean test' }
            post { always { junit 'target/surefire-reports/*.xml' } }
        }
        stage('Build Fat JAR') {
            steps {
                sh 'mvn -B -ntp package -DskipTests'
                sh '''
                    jar tf target/springboot-midterm-1.0.0.jar > target/jar-contents.txt
                    grep 'BOOT-INF/classes/com/example/midterm/MidtermApplication.class' target/jar-contents.txt
                    grep 'BOOT-INF/lib/spring-boot-' target/jar-contents.txt
                    ls -lh target/springboot-midterm-1.0.0.jar
                '''
            }
        }
        stage('Smoke Test') {
            steps { sh 'sh scripts/smoke-test.sh' }
        }
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/springboot-midterm-1.0.0.jar,target/jar-contents.txt', fingerprint: true
            }
        }
    }
}
