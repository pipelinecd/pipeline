package org.pipelinelabs.pipeline.servicediscovery.resources

import io.dropwizard.jersey.caching.CacheControl

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE

@Path('/ping')
class PingResource {
    private static final String CONTENT = "pong";

    @GET
    @CacheControl(mustRevalidate = true, noCache = true, noStore = true)
    def get() {
        return Response.ok(CONTENT).type(TEXT_PLAIN_TYPE).build()
    }
}
