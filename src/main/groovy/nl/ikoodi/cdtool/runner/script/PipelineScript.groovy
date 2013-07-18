package nl.ikoodi.cdtool.runner.script

import nl.ikoodi.cdtool.dsl.PipelineDsl

public abstract class PipelineScript extends Script {

    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
