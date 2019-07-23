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
