package cd.pipeline.listen.resources

import cd.pipeline.listen.core.GitTriggerEvent
import com.google.common.eventbus.EventBus
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import javax.ws.rs.*
import javax.ws.rs.core.Response

import static cd.pipeline.listen.core.git.GitUtils.toBranchName
import static cd.pipeline.listen.core.git.GithubUtils.toGithubSshUrl
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED
import static javax.ws.rs.core.MediaType.APPLICATION_JSON
import static javax.ws.rs.core.Response.Status.BAD_REQUEST

@Slf4j
@Path("/providers/github")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_FORM_URLENCODED)
class GitHubWebHookResource {

    private final EventBus bus

    GitHubWebHookResource(EventBus bus) {
        this.bus = bus
    }

    @POST
    Response trigger(@FormParam('payload') String payload) {
        def slurper = new JsonSlurper()
        def info = slurper.parseText(payload)
        try {
            verifyRequest(info)
        } catch (AssertionError e) {
            log.warn("Received GitHub WebHook payload with unexpected format:\n{}", e.message)
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
        def url = toGithubSshUrl(request.repository.url)
        def branch = toBranchName(request.ref)
        new GitTriggerEvent(url, branch)
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
        assertValidUri(request.repository.url)
    }

    private assertValidUri(String uri) throws AssertionError {
        try {
            new URI(uri)
        } catch (URISyntaxException e) {
            throw new AssertionError(e.message, e)
        }
    }
}
