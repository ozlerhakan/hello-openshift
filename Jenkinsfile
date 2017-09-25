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
      env.PATH = "${dockerHome}:${mvnHome}/bin:${env.PATH}"
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
   }
}

def getImageVersion(){
    "0.0.1"
}

def dockerBuild(containerName, imageVersion){
    try {
        sh "docker rmi -f `docker images *$containerName* -q`"
    } catch(error){}
    sh "docker build -t $containerName:latest  -t $containerName:$imageVersion ."
}

def dockerPush(containerName, dockerHubUser, imageVersion){
    sh "docker tag $containerName:latest $dockerHubUser/$containerName:latest"
    sh "docker tag $containerName:$imageVersion $dockerHubUser/$containerName:$imageVersion"
    sh "docker push $dockerHubUser/$containerName:$imageVersion"
    sh "docker push $dockerHubUser/$containerName:latest"
}