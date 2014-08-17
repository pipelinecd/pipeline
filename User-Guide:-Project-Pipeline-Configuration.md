# Configuring the Pipeline Configuration for your project

The configuration of a project pipeline is stored in the file `project.pipe` in the root of the code repository of the project, and versioned together with the code. When referring to a pipeline, we talk about the configuration within the `project.pipe` file.

Currently the following configuration is supported in the pipeline:
```groovy
stage {NAME}, {
    description = '{DESC}'
    run '{COMMAND}'
}
```
This defines one step in the pipeline, which is called a "stage".
Replace `NAME` with a simple name of the stage, `DESC` can be a more descriptive description of this stage (even multi-line), and `COMMAND` should be the command to execute during this stage.

The pipeline can exist of multiple stages, which are executed in order of configuring.
When a stage command exits with an error status, the whole pipeline is stopped and set as failed.

As there's no GUI of any kind to Pipeline, the way to get notified of failed or succeeding pipeline runs is (currently) by email. To get notifications, add the following configuration to the pipeline:

```groovy
messenger {
    email {
        host = 'smtp.gmail.com'
        port = 587
        tls  = true
        username = 'user@site.com'
        password = 'password'
        from = 'from@site.org'
    }
}

announce {
    email {
        to 'EMAIL'
    }
}
```
Replace `EMAIL` with the emailaddress to sent notifications to.

> **WARNING** 
> Don't store your email server password in a __public__ git repository. Even in a __private__ repository be warned that this password will stay in your history, so for collaborators or when the repository eventually gets public, can recover your password from the git history. See Github' [Remove sensitive data](https://help.github.com/articles/remove-sensitive-data) article for details.
>
> The email functionality is **NOT** required, if you don't add the messenger and announce sections to your pipeline configuration, the pipeline will work just fine. Without it, the only way to see if the pipeline succeeded/failed is in the pipe-listen log.
__(in the future there will be a secure solution for this)__

Example pipeline
================
An example pipeline configuration could look as following:

```groovy
messenger {
    email {
        host = 'smtp.gmail.com'
        port = 587
        tls  = true
        username = 'user@site.com'
        password = 'password'
        from = 'from@site.org'
    }
}

stage commit, {
    description = 'Compile and test'
    run 'mvn test'
}

stage component-test, {
    description = 'Run component tests'
    run 'mvn verify -DskipTests=true -DskipITs=false'
}

stage document, {
    description = 'Generate documentation'
    run 'mvn -P generate-docs'
}

stage inspect, {
    description = 'Inspect the quality'
    run 'mvn sonar:sonar'
}

stage snapshot-release, {
    description = 'Do a snapshot release'
    run 'mvn deploy'
}

announce {
    email {
        to 'list@mailing.org'
    }
}
```
Which will execute the following commands in order:
- `mvn test`
- `mvn verify -DskipTests=true -DskipITs=false`
- `mvn -P generate-docs`
- `mvn sonar:sonar`
- `mvn deploy`
- sent email to `list@mailing.org` notifying about the pipeline status