package org.pipelinelabs.pipeline.listen

import com.yammer.dropwizard.config.Environment
import org.pipelinelabs.pipeline.listen.core.DeadEventHandler
import org.pipelinelabs.pipeline.listen.core.GitWorker
import org.pipelinelabs.pipeline.listen.resources.GitHubWebHookResource
import spock.lang.Specification

import java.util.concurrent.ExecutorService

class PipeListenServiceSpec extends Specification {

    def 'Service serves the GitHubWebHookResource'() {
        given:
        def env = Mock(Environment)
        def executor = Mock(ExecutorService)
        def service = new PipeListenService()
        def config = new PipeListenConfiguration()

        when:
        service.run(config, env)

        then:
        1 * env.managedExecutorService(_, _, _, _, _) >> executor
        1 * env.addResource(_ as GitHubWebHookResource)
    }

    def 'Service manages the GitWorker'() {
        given:
        def env = Mock(Environment)
        def executor = Mock(ExecutorService)
        def service = new PipeListenService()
        def config = new PipeListenConfiguration()

        when:
        service.run(config, env)

        then:
        1 * env.managedExecutorService(_, _, _, _, _) >> executor
        1 * env.manage(_ as GitWorker)
    }

    def 'Service manages the DeadEventHandler'() {
        given:
        def env = Mock(Environment)
        def executor = Mock(ExecutorService)
        def service = new PipeListenService()
        def config = new PipeListenConfiguration()

        when:
        service.run(config, env)

        then:
        1 * env.managedExecutorService(_, _, _, _, _) >> executor
        1 * env.manage(_ as DeadEventHandler)
    }
}
