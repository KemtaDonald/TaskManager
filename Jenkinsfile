pipeline {
  agent none
  environment{
    DEPLOY='placeholder'
  }
  stages {
    stage('Build') {
      agent {label 'agent-permanent'}
      steps {
        checkout scm
        echo '=== Build: Génération du .jar ==='
        sh 'mvn clean package -DskipTests'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }
    stage('test') {
      agent {label 'AGENT1'}
      steps {
        checkout scm
        echo '=== Tests: unitaires + intégration ==='
        sh 'mvn test verify'
        junit '/target/surefire-reports/TEST*.xml'
      }
    }
    stage('Deploy') {
      agent {label 'agent-permanent'}
      steps {
        sh 'sleep 4'
        sh "echo $DEPLOY"
      }
    }

  }
}
