package org.pipelinelabs.pipeline.cli.dsl.script

import org.pipelinelabs.pipeline.dsl.PipelineDsl

public abstract class PipelineScript extends Script {
    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
