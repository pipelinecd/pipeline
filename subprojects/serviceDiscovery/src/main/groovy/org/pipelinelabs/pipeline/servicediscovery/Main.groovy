package org.pipelinelabs.pipeline.servicediscovery

import io.dropwizard.Application

class Main {

    static void main(String... args) {
        new Main().createApplication().run(args)
    }

    Application createApplication() {
        new PipeServiceDiscoveryApplication()
    }
}

