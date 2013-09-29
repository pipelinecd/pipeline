package org.pipelinelabs.pipeline.listen.core.healthcheck

import com.yammer.metrics.core.HealthCheck

import static com.yammer.metrics.core.HealthCheck.Result.healthy
import static com.yammer.metrics.core.HealthCheck.Result.unhealthy

class GitCommandHealthCheck extends HealthCheck {

    GitCommandHealthCheck() {
        super('git command available')
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        def proc = "git --version".execute()
        if (proc.waitFor() == 0) {
            return healthy()
        }
        return unhealthy('[git] command not available on PATH')
    }
}
