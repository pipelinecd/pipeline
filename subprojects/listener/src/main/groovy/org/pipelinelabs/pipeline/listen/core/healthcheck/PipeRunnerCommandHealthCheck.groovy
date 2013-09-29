package org.pipelinelabs.pipeline.listen.core.healthcheck

import com.yammer.metrics.core.HealthCheck

import static com.yammer.metrics.core.HealthCheck.Result.healthy
import static com.yammer.metrics.core.HealthCheck.Result.unhealthy

class PipeRunnerCommandHealthCheck extends HealthCheck {

    PipeRunnerCommandHealthCheck() {
        super('pipe-runner command available')
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        def proc
        try {
            proc = "pipe-runner help".execute()
        } catch (IOException e) {
            return unhealthy(e.message)
        }
        if (proc.waitFor() == 0) {
            return healthy()
        }
        return unhealthy('[pipe-runner] command not available on PATH')
    }
}
