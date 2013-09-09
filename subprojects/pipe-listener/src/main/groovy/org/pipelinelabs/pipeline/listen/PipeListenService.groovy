package org.pipelinelabs.pipeline.listen

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import org.pipelinelabs.pipeline.listen.resources.GitHubWebHookResource

class PipeListenService extends Service<PipeListenConfiguration> {

    @Override
    void initialize(Bootstrap<PipeListenConfiguration> bootstrap) {
    }

    @Override
    void run(PipeListenConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new GitHubWebHookResource())
    }
}
