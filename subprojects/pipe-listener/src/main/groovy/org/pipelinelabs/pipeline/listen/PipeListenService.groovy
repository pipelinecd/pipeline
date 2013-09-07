package org.pipelinelabs.pipeline.listen

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import org.pipelinelabs.pipeline.listen.core.Providers
import org.pipelinelabs.pipeline.listen.core.providers.GitHubWebHook
import org.pipelinelabs.pipeline.listen.resources.ProvidersResource

class PipeListenService extends Service<PipeListenConfiguration> {

    @Override
    void initialize(Bootstrap<PipeListenConfiguration> bootstrap) {
    }

    @Override
    void run(PipeListenConfiguration configuration, Environment environment) throws Exception {
        registerWebHookProviders(environment)
    }

    private void registerWebHookProviders(Environment environment) {
        def providers = new Providers()
        providers.add(new GitHubWebHook())
        environment.addResource(new ProvidersResource(providers))
    }
}
