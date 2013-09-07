package org.pipelinelabs.pipeline.listen

import com.yammer.dropwizard.config.Environment
import org.pipelinelabs.pipeline.listen.resources.ProvidersResource
import spock.lang.Specification

class PipeListenServiceSpec extends Specification {

    def 'Service serves the ProvidersResource'() {
        given:
        def env = Mock(Environment)
        def service = new PipeListenService()
        def config = new PipeListenConfiguration()

        when:
        service.run(config, env)

        then:
        1 * env.addResource(_ as ProvidersResource)
    }
}