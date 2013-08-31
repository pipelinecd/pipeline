import groovy.json.JsonSlurper

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT
import static io.netty.handler.codec.http.HttpResponseStatus.UNPROCESSABLE_ENTITY
import static org.ratpackframework.groovy.RatpackScript.ratpack

ratpack {
    handlers {
        get() {
            response.send "Hellow World lalala"
        }
        post('providers/github') {
            // Valid request?
            if (!request.form.containsKey('payload')) {
                // Nope, let the caller know
                clientError(UNPROCESSABLE_ENTITY.code())
            }

            final slurper = new JsonSlurper()
            final payload = slurper.parseText(request.form.payload)
            // Basic input validation
            try {
                assert payload.commits
                assert payload.head_commit
                assert payload.pusher.email
                assert payload.pusher.name
                assert payload.ref
                assert payload.repository.name
                assert payload.repository.language
                assert payload.repository.private == false
            } catch (AssertionError e) {
                clientError(UNPROCESSABLE_ENTITY.code())
            }
            // TODO Handle request, put it in a queue or something

            // Return success
            response.status(NO_CONTENT.code())
            response.send()
        }
    }
}