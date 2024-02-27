pipeline {
    agent any
    environment{
        dir="/var/jenkins_home/workspace/book"
        ip="192.168.83.10"
    }
    stages {

    stage("maven build") {
        agent {
            docker {
                image 'maven:latest'
                args '-v /root/.m2/repository:/root/.m2/repository'
            }
        }
        steps {
            sh 'mvn package  -DskipTests -f $dir/backend'
        }
    }
    stage("build images"){
        steps{
            sh"""
            docker build -t $ip/book/backend $workspace/backend
            docker build -t $ip/book/bookdb $workspace/bookdb
            docker build -t $ip/book/front $workspace/front
            """
        }
    }
    stage("push images"){
        steps{
            sh """
            docker login $ip -uadmin -pHarbor12345
            docker push $ip/book/backend
            docker push $ip/book/bookdb
            docker push $ip/book/front
            """
        }
    }
    stage("apply"){
        steps{
            sh """ 
                sed -i "s#BACKEND#$ip/book/backend#g" $workspace/yaml/backend.yaml
                sed -i "s#FRONT#$ip/book/front#g" $workspace/yaml/front.yaml
                sed -i "s#BOOKDB#$ip/book/bookdb#g" $workspace/yaml/bookdb.yaml
                kubectl apply -f $workspace/yaml/backend.yaml
                kubectl apply -f $workspace/yaml/bookdb.yaml
                kubectl apply -f $workspace/yaml/front.yaml
            """
            }
        }
    }
}
