package org.pipelinelabs.pipeline.runner.cli.dsl.script

import org.pipelinelabs.pipeline.runner.dsl.PipelineDsl

public abstract class PipelineScript extends Script {
    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
