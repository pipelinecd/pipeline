package org.pipelinelabs.pipeline.listen.resources

import com.google.common.eventbus.EventBus
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.pipelinelabs.pipeline.listen.core.GitTriggerEvent

import javax.ws.rs.*
import javax.ws.rs.core.Response

import static javax.ws.rs.core.MediaType.APPLICATION_JSON
import static javax.ws.rs.core.Response.Status.BAD_REQUEST

@Slf4j
@Path("/providers/github")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class GitHubWebHookResource {

    private final EventBus bus

    GitHubWebHookResource(EventBus bus) {
        this.bus = bus
    }

    @POST
    Response trigger(@FormParam('payload') String payload) {
        final slurper = new JsonSlurper()
        def info = slurper.parseText(payload)
        try {
            verifyRequest(info)
        } catch (AssertionError e) {
            log.warn("Received GitHub WebHook payload with unexpected format:\n{}", info)
            return Response.status(BAD_REQUEST.statusCode).build()
        }
        return handleRequest(info)
    }

    private Response handleRequest(request) {
        def event = new GitTriggerEvent(request.repository.url)
        bus.post(event)
        Response.noContent().build()
    }

    private verifyRequest(request) throws AssertionError {
        assert request.commits
        assert request.head_commit
        assert request.pusher
        assert request.pusher.email
        assert request.pusher.name
        assert request.ref
        assert request.repository
        assert request.repository.name
        assert request.repository.master_branch
        assert request.repository.language
        assert request.repository.private == true ||
                request.repository.private == false
        assert request.repository.url
    }
}
