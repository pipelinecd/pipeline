package org.pipelinelabs.pipeline.listen.resources

import com.google.common.eventbus.EventBus
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
import com.yammer.dropwizard.testing.ResourceTest
import org.pipelinelabs.pipeline.listen.core.GitTriggerEvent
import spock.lang.Specification
import spock.lang.Unroll

import static com.sun.jersey.api.client.ClientResponse.Status.BAD_REQUEST
import static com.sun.jersey.api.client.ClientResponse.Status.NO_CONTENT
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE

class GitLabWebHookResourceSpec extends Specification {

    def eventBus = Mock(EventBus)
    TestResource resource = new TestResource(eventBus)

    def 'Valid payload results in git event with gitlab ssh url on queue'() {
        given:
        def payload = jsonFixture("gitlab/samplePayload.json")

        when:
        def response = requestBuilder()
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .post(ClientResponse, payload)

        then:
        1 * eventBus.post(_ as GitTriggerEvent) >> { GitTriggerEvent event ->
            assert event.url == 'git@git.domain.com:group/project.git'
        }
        response.type == APPLICATION_JSON_TYPE
        response.clientResponseStatus == NO_CONTENT
        response.length == -1
    }

    def 'Valid payload results in git event on queue'() {
        given:
        def payload = jsonFixture("gitlab/samplePayload.json")

        when:
        def response = requestBuilder()
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .post(ClientResponse, payload)

        then:
        1 * eventBus.post(_ as GitTriggerEvent)
        response.type == APPLICATION_JSON_TYPE
        response.clientResponseStatus == NO_CONTENT
        response.length == -1
    }

    @Unroll
    def "POST with '#type' payload results in 204 NO_CONTENT"() {
        given:
        def payload = jsonFixture("gitlab/${fixture}")

        when:
        def response = requestBuilder()
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .post(ClientResponse, payload)

        then:
        response.type == APPLICATION_JSON_TYPE
        response.clientResponseStatus == NO_CONTENT
        response.length == -1

        where:
        type              | fixture
        'complete sample' | 'samplePayload.json'
        'minimal sample'  | 'minimalPayload.json'
    }

    @Unroll
    def "POST missing '#missing' in payload results in 400 BAD_REQUEST"() {
        given:
        def payload = jsonFixture("gitlab/${fixture}")

        when:
        def response = requestBuilder()
                .type(APPLICATION_JSON_TYPE)
                .accept(APPLICATION_JSON_TYPE)
                .post(ClientResponse, payload)

        then:
        response.type == APPLICATION_JSON_TYPE
        response.clientResponseStatus == BAD_REQUEST

        where:
        missing               | fixture
        'commits'             | 'minimalPayloadMissingCommits.json'
        'pusher'              | 'minimalPayloadMissingPusher.json'
        'ref'                 | 'minimalPayloadMissingRef.json'
        'repository.homepage' | 'minimalPayloadMissingRepositoryHomepage.json'
        'repository.name'     | 'minimalPayloadMissingRepositoryName.json'
        'repository.url'      | 'minimalPayloadMissingRepositoryUrl.json'
    }

    def setup() {
        resource.setUpJersey()
    }

    def cleanup() {
        resource.tearDownJersey()
    }

    private WebResource.Builder requestBuilder() {
        return resource.client().resource('/providers/gitlab').requestBuilder
    }

    private class TestResource extends ResourceTest {

        private EventBus bus

        TestResource(EventBus bus) {
            this.bus = bus
        }

        @Override
        protected void setUpResources() throws Exception {
            addResource(new GitLabWebHookResource(bus))
        }

        @Override
        protected Client client() {
            return super.client()
        }
    }
}
