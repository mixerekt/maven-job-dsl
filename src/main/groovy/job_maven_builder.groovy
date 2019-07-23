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
                        credentials(this.credentialsId)
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
    def builder = new MavenJobBuilder(jobName: "Maven Job",
            description: "simple maven job builder",
            gitUrl: "https://github.com/mixerekt/testing-job-repo.git",
            branchName: "master",
            credentialsId: "c69356b9-e9d8-46d6-b2d6-0fe73f49d2e4",
            numToKeep: 10,
            daysToKeep: 20)

    builder.build(this)
}
