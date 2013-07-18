package nl.ikoodi.cdtool.cli.dsl.script

import nl.ikoodi.cdtool.dsl.PipelineDsl

public abstract class PipelineScript extends Script {

    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
