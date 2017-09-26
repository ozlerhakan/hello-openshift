def imageVersion
def dockerHubUser = "kodcu"
def containerName = "hello"

node {
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git branch: 'http', poll: false, url: 'https://github.com/ozlerhakan/hello'
      
      // Get the docker client
      def dockerHome = tool 'docker-17-06'
      // Get the Maven tool.
      def mvnHome = tool 'maven-3.5'
      env.PATH = "${dockerHome}/bin:${mvnHome}/bin:${env.PATH}"
   }

   stage('Build') {
      // Run the maven build
      sh 'ls -lha'
      sh "mvn clean install" 
   }

   stage('Publish to Docker Registry') {
      imageVersion = getImageVersion()
      dockerBuild(containerName, imageVersion)
      dockerPush(containerName, dockerHubUser, imageVersion)
   }

   stage('Deploy') {
      openshiftDeploy apiURL: '', authToken: '', depCfg: 'hello-backend-from-jenkins', namespace: 'demo', verbose: 'true', waitTime: '5', waitUnit: 'min'
      openshiftVerifyDeployment apiURL: '', authToken: '', depCfg: 'hello-backend-from-jenkins', namespace: 'demo', replicaCount: '1', verbose: 'true', verifyReplicaCount: 'true', waitTime: '5', waitUnit: 'min'

   }

}

def getImageVersion(){
    "0.0.1"
}

def dockerBuild(containerName, imageVersion){
    try {
        sh "docker -H unix:///var/run/docker.sock image prune -f"
    } catch(error){}
    sh "docker -H unix:///var/run/docker.sock build -f Dockerfile.java -t $containerName:latest  -t $containerName:$imageVersion --pull --no-cache ."
}

def dockerPush(containerName, dockerHubUser, imageVersion){
  withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      sh "docker -H unix:///var/run/docker.sock tag $containerName:latest $dockerHubUser/$containerName:latest"
      sh "docker -H unix:///var/run/docker.sock tag $containerName:$imageVersion $dockerHubUser/$containerName:$imageVersion"
      sh "docker -H unix:///var/run/docker.sock push $dockerHubUser/$containerName:$imageVersion"
      sh "docker -H unix:///var/run/docker.sock push $dockerHubUser/$containerName:latest"
    }
}