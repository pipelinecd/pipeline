package nl.ikoodi.io.cy.runner.script

import nl.ikoodi.io.cy.dsl.PipelineDsl

public abstract class PipelineScript extends Script {

    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
