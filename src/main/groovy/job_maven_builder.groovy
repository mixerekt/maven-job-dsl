import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

class MavenJobBuilder {
    def jobName
    def description
    def gitUrl
    def branchName
    def credentialsId
    def numToKeep
    def daysToKeep

    Job build(DslFactory dslFactory) {
        dslFactory.mavenJob(jobName) {
            description(description)
            logRotator {
                numToKeep = this.numToKeep
                daysToKeep = this.daysToKeep
            }
            scm {
                git {
                    remote {
                        name("origin")
                        url(gitUrl)
                        credentialsId(this.credentialsId)
                    }
                    branch(branchName)
                }
            }
            goals('clean package')
        }
    }
}

folder('git-maven-folder') {
    description('This is folder for maven builder')
}

def builder = new MavenJobBuilder(jobName: "Maven Job",
        description: "simple maven job builder",
        gitUrl: "",
        branchName: "master",
        credentialsId: "",
        numToKeep: 10,
        daysToKeep: 20)

builder.build(this)
