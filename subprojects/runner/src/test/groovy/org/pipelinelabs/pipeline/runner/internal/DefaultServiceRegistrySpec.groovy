package org.pipelinelabs.pipeline.runner.internal

import org.pipelinelabs.pipeline.dsl.PipelineDsl
import org.pipelinelabs.pipeline.dsl.internal.DefaultPipelineDsl
import org.pipelinelabs.pipeline.util.ServiceLookupException
import spock.lang.Specification
import spock.lang.Unroll

class DefaultServiceRegistrySpec extends Specification {
    final registry = new DefaultServiceRegistry()

    @Unroll
    def "Provides #impl.simpleName for #api.simpleName"() {
        expect:
        assert impl.isInstance(registry.get(api))

        where:
        api         || impl
        PipelineDsl || DefaultPipelineDsl
    }

    def "Throws a ServiceLookupException when asking for an unknown implementation"() {
        when:
        registry.get(UnknownApi)

        then:
        def e = thrown(ServiceLookupException)
        assert e.message.contains(UnknownApi.class.simpleName)
    }
}

interface UnknownApi {}
