package nl.ikoodi.io.cy.runner.script

import nl.ikoodi.io.cy.dsl.api.Pipeline

public abstract class PipelineScript extends Script {

    @Delegate
    private Pipeline pipeline;

    public void init(final Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
