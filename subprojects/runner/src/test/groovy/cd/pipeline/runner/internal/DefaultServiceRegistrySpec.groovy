package cd.pipeline.runner.internal

import cd.pipeline.dsl.PipelineDsl
import cd.pipeline.dsl.internal.DefaultPipelineDsl
import cd.pipeline.util.ServiceLookupException
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
