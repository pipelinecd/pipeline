# Global Design

## Legenda
* **DSL**
  Domain Specific Language
* **Pipeline definition**
  Defines the configurable pipeline that can be reused to be run with different execution configurations
* **Execution configuration**
  Configuration to configure the pipeline definition with to give the pipeline meaning

## Pipeline definition
The pipeline definition script is a DSL implemented in Groovy focussed on the domain of pipelines in the sense of Continuous-Integration (CI), -Delivery (CD), -Deployment, Release Management, Release Orchestration. Or as the total picture is often called "Application Lifecycle Management" (ALM).

The pipeline definition is configurable. The execution configuration of the pipeline definition is decoupled from the pipeline definition so a single pipeline definition can be executed with different execution configurations.
This makes it possible, to just have one pipeline definition and as many execution configurations you want without the need of "cascading project" or "template inheritance" as implemented by other CI tools.

### Example configurations

#### Simple Continous Integration pipeline
Pipeline definition:

    environment {
        mvn: '3.0.5'
        git: '1.7.9.5'
        env.vars {
            EDITOR: 'nano'
        }
    }

    configuration {
        scm(name: latest, type: git) {
            url:           assertGitUrl
            branch:        assertString
            username:      assertString
            email:         assertEmail
            key {
                type:      assertString
                private:   assertString
            }
        }
    }

    environment.afterConfigure {
        prepare scm {
            file('.gitconfig') << '''
                [user]
                name = ${scm.latest.username}
                email = ${scm.latest.email}
            '''
            file('.ssh/id_rsa') << '${scm.latest.key.private}'
        }

        verify scm {
            run 'git ls-remote --exit-code ${scm.latest.url} ${scm.latest.branch}'
        }
    }

    stage commit {
        title: 'Compile and Test'
        run {
            env.vars {
                SOME_VAR: 'some value'
            }
            command: git clone ${scm.latest.url} --branch ${scm.latest.branch}
        }
        run 'mvn clean install'
    }

    stage document(dependsOn: commit) {
        description: 'Generate documentation'
        run 'mvn -P generate-docs'
    }

    stage inspect(dependsOn: commit) {
        description: 'Inspect the quality'
        run 'mvn sonar:sonar'
    }

    stage snapshot-release(dependsOn: inspect) {
        description: 'Do a snapshot release'
        run 'mvn deploy'
    }

    announce(type: email) {
        to: 'list@mailing.org'
    }

Required configuration to provide for pipeline:

    scm(name: latest, type: 'git') {
        url:           'git@github.com/myuser/myrepo'
        branch:        'develop'
        username:      'CI server'
        email:         'ci@mydomain.tld'
        key.private:   '....'
    }

When executed, the above pipeline would do the following:

1. Build an execution model based on provided configuration and the pipeline definition, containing:
    * a model that represents the required environment
    * a configuration model based on provided configuration
    * a configuration verification model based the configuration section of the pipeline definition
    * a Directed Acyclic Graph (DAG) of the stages that looks as following:
        * _commit_
            * _document_
            * _inspect_
                * _snapshot-release_

2. Prepare an execution environment containing:
    * maven version 3.0.5 on the PATH
    * git version 1.7.9.5 on the PATH
3. Verify that the provided configuration is valid according to the pipeline definition
4. Do some additional environment preparation and verification after the configuration is found valid
   (eg. Verify that the provided scm configuration is correct by trying to connect)
5. Start executing the _commit_ stage, as that's the starting point of this graph of stages
    1. Start stage _document_
    2. Start stage _inspect_
        1. Start stage _snapshot-release_

If anything fails in this pipeline, it directly exists with a failure status.
The _document_ and _inspect_ stages could be started in parallel. As they're on the same level, if one of them fails, the other will continue to run until it finished (succesful or not). At that point the pipeline will exist with a failure status.
