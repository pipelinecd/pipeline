package org.pipelinelabs.pipeline.servicediscovery

import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.pipelinelabs.pipeline.servicediscovery.resources.PingResource
import org.pipelinelabs.pipeline.servicediscovery.resources.RootResource

class ServiceDiscoveryApplication extends Application<ServiceDiscoveryConfiguration> {

    @Override
    void initialize(Bootstrap<ServiceDiscoveryConfiguration> bootstrap) {
    }

    @Override
    void run(ServiceDiscoveryConfiguration config, Environment env) throws Exception {
        env.jersey().register(new RootResource())
        env.jersey().register(new PingResource())
    }
}
