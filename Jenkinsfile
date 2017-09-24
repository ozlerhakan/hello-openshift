node {
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git branch: 'http', poll: false, url: 'https://github.com/ozlerhakan/hello'
        
      // Get the Maven tool.
      def mvnHome = tool 'maven-3.5'
      env.PATH = "${mvnHome}/bin:${env.PATH}"
   }
   stage('Build') {
      // Run the maven build
        sh 'ls -lha'
        sh "mvn clean install" 
   }
//   stage('Deploy') {
//      openshiftDeploy(namespace: 'demo', deploymentConfig: 'hello')
//   }
}