pipeline {
  agent none
  stages {
    stage('Build') {
      agent {label 'AGENT1'}
      steps {
        echo 'first stage build'
        echo 'First build'
      }
    }
    stage('test') {
      agent {label 'agent-permanent'}
      steps {
        sh 'echo first stage build'
        sh 'sleep 5'
      }
    }
    stage('Deploy') {
      agent {label 'agent-permanent'}
      steps {
        sh 'sleep 5'
        sh 'placeholder'
      }
    }

  }
}
