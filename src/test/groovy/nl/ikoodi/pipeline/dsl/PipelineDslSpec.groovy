package nl.ikoodi.pipeline.dsl

import nl.ikoodi.pipeline.api.Pipeline
import nl.ikoodi.pipeline.dsl.internal.DefaultPipelineDsl
import spock.lang.Specification

class PipelineDslSpec extends Specification {

    def "Pipeline DSL exports to Pipeline implementation"() {
        PipelineDsl pipe = new DefaultPipelineDsl()

        when:
        pipe.stage "stageName", {}

        then:
        Pipeline actual = pipe.export()
        assert actual.stages != null
        assert actual.stages.size() == 1
    }
}
