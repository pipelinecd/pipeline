package cd.pipeline.listen.resources

import cd.pipeline.listen.core.GitTriggerEvent
import com.google.common.eventbus.EventBus
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

import static cd.pipeline.listen.core.git.GitUtils.toBranchName
import static javax.ws.rs.core.MediaType.APPLICATION_JSON
import static javax.ws.rs.core.Response.Status.BAD_REQUEST

@Slf4j
@Path("/providers/gitlab")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class GitLabWebHookResource {

    private final EventBus bus

    GitLabWebHookResource(EventBus bus) {
        this.bus = bus
    }

    @POST
    Response trigger(String payload) {
        def slurper = new JsonSlurper()
        def info = slurper.parseText(payload)
        try {
            verifyRequest(info)
        } catch (AssertionError e) {
            log.warn("Received GitLab WebHook payload with unexpected format:\n{}", e.message)
            return Response.status(BAD_REQUEST.statusCode).build()
        }
        return handleRequest(info)
    }

    private Response handleRequest(request) {
        def event = createGitTriggerEvent(request)
        bus.post(event)
        Response.noContent().build()
    }

    private createGitTriggerEvent(request) {
        def url = request.repository.url
        def branch = toBranchName(request.ref)
        new GitTriggerEvent(url, branch)
    }

    private verifyRequest(request) throws AssertionError {
        assert request.commits
        assert request.user_name
        assert request.ref
        assert request.repository
        assert request.repository.name
        assert request.repository.homepage
        assert request.repository.url
        assertValidUri(request.repository.url)
    }

    private assertValidUri(String uri) throws AssertionError {
        assert uri.contains('@')
        def checkUri = uri.substring(uri.indexOf('@') + 1)
        try {
            new URI(checkUri)
        } catch (URISyntaxException e) {
            throw new AssertionError(e.message, e)
        }
    }
}
