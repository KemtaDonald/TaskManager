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
        sh 'sleep 4'
      }
    }
    stage('Deploy') {
      agent {label 'agent-permanent'}
      steps {
        sh 'sleep 4'
        sh 'echo placeholder'
      }
    }

  }
}
