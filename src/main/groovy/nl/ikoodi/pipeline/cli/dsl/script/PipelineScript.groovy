package nl.ikoodi.pipeline.cli.dsl.script

import nl.ikoodi.pipeline.dsl.PipelineDsl

public abstract class PipelineScript extends Script {

    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
