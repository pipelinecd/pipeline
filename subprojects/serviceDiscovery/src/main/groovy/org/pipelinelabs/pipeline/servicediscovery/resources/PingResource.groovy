package org.pipelinelabs.pipeline.servicediscovery.resources

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.CacheControl
import javax.ws.rs.core.Response

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE

@Path('/ping')
class PingResource {
    private static final String CONTENT = "pong";

    @GET
    def get() {
        def cacheControl = new CacheControl()
        cacheControl.with {
            mustRevalidate = true
            noCache = true
            noStore = true
        }
        return Response.ok(CONTENT).type(TEXT_PLAIN_TYPE).cacheControl(cacheControl).build()
    }
}
