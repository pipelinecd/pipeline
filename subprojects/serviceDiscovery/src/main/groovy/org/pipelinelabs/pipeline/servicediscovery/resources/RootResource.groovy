package org.pipelinelabs.pipeline.servicediscovery.resources

import javax.ws.rs.GET
import javax.ws.rs.Path

@Path('/')
class RootResource {

    @GET
    def get() {
        return "hello"
    }
}
