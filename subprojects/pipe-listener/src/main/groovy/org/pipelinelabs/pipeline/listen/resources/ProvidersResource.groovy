package org.pipelinelabs.pipeline.listen.resources

import org.pipelinelabs.pipeline.listen.core.Providers

import javax.ws.rs.*
import javax.ws.rs.core.Response

import static javax.ws.rs.core.MediaType.APPLICATION_JSON
import static javax.ws.rs.core.Response.Status.NOT_FOUND

@Path("/providers/{provider}")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class ProvidersResource {

    private final Providers providers;

    ProvidersResource(Providers providers) {
        this.providers = providers
    }

    @GET
    Response isAvailable(@PathParam('provider') String provider) {
        if (!providers.hasProviderFor(provider)) {
            return Response.status(NOT_FOUND).build()
        }
        return Response.noContent().build()
    }

    @POST
    Response trigger() {
        return Response.noContent().build();
    }
}
