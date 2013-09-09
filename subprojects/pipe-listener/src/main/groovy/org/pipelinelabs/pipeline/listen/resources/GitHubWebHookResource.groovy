package org.pipelinelabs.pipeline.listen.resources

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import javax.ws.rs.*
import javax.ws.rs.core.Response

import static javax.ws.rs.core.MediaType.APPLICATION_JSON
import static javax.ws.rs.core.Response.Status.BAD_REQUEST

@Slf4j
@Path("/providers/github")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class GitHubWebHookResource {

    @POST
    Response trigger(@FormParam('payload') String payload) {
        final slurper = new JsonSlurper()
        def info = slurper.parseText(payload)
        try {
            assert info.commits
            assert info.head_commit
            assert info.pusher
            assert info.pusher.email
            assert info.pusher.name
            assert info.ref
            assert info.repository
            assert info.repository.name
            assert info.repository.master_branch
            assert info.repository.language
            assert info.repository.private == true ||
                    info.repository.private == false
            assert info.repository.url
        } catch (AssertionError e) {
            log.warn("Received GitHub WebHook payload with unexpected format:\n{}", info)
            return Response.status(BAD_REQUEST.statusCode).build()
        }
        return Response.noContent().build()
    }
}
