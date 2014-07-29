package cd.pipeline.listen

import cd.pipeline.listen.core.DeadEventHandler
import cd.pipeline.listen.core.GitWorker
import cd.pipeline.listen.core.healthcheck.GitCommandHealthCheck
import cd.pipeline.listen.core.healthcheck.PipeRunnerCommandHealthCheck
import cd.pipeline.listen.resources.GitHubWebHookResource
import cd.pipeline.listen.resources.GitLabWebHookResource
import com.google.common.eventbus.AsyncEventBus
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment

import static java.util.concurrent.TimeUnit.SECONDS

class PipeListenService extends Service<PipeListenConfiguration> {

    @Override
    void initialize(Bootstrap<PipeListenConfiguration> bootstrap) {
    }

    @Override
    void run(PipeListenConfiguration config, Environment env) throws Exception {
        def bus = createEventBus(env)
        env.manage(new DeadEventHandler(bus))
        env.manage(new GitWorker(bus));
        env.addResource(new GitHubWebHookResource(bus))
        env.addResource(new GitLabWebHookResource(bus))
        env.addHealthCheck(new GitCommandHealthCheck())
        env.addHealthCheck(new PipeRunnerCommandHealthCheck())
    }

    private createEventBus(Environment env) {
        final corePoolSize = 2
        final maxPoolSize = 6
        final keepAliveTimeInSeconds = 30
        new AsyncEventBus(
                env.managedExecutorService(
                        "eventbus-worker-%s",
                        corePoolSize,
                        maxPoolSize,
                        keepAliveTimeInSeconds, SECONDS
                )
        )
    }
}
