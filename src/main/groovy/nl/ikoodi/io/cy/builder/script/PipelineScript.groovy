package nl.ikoodi.io.cy.builder.script

import nl.ikoodi.io.cy.api.Pipeline

public abstract class PipelineScript extends Script {

    @Delegate
    private Pipeline pipeline;

    public void init(final Pipeline pipeline) {
        this.pipeline = pipeline;
    }

}
