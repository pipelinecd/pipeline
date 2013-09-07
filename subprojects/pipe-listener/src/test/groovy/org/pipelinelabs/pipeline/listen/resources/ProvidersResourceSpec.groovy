package org.pipelinelabs.pipeline.listen.resources

import org.pipelinelabs.pipeline.listen.core.Providers
import spock.lang.Ignore
import spock.lang.Specification

import static javax.ws.rs.core.Response.Status.NOT_FOUND
import static javax.ws.rs.core.Response.Status.NO_CONTENT

class ProvidersResourceSpec extends Specification {
    def providers = Mock(Providers)
    def resource = new ProvidersResource(providers)

    def 'GET return 404 (Not Found) for unknown provider'() {
        when:
        def response = resource.isAvailable('unknown')

        then:
        1 * providers.hasProviderFor(_) >> false
        response.status == NOT_FOUND.statusCode
    }

    def 'GET return 204 (No Content) for known provider'() {
        when:
        def response = resource.isAvailable('known')

        then:
        1 * providers.hasProviderFor(_) >> true
        response.status == NO_CONTENT.statusCode
    }

    @Ignore('Needs implementation')
    def 'POST accepts provider specific notification format'() {
        expect:
        false
    }

    @Ignore('Needs implementation')
    def 'Accepted POST adds notification to worker queue'() {
        expect:
        false
    }
}
