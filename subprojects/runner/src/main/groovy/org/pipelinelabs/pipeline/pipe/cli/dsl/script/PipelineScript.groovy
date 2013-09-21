package org.pipelinelabs.pipeline.pipe.cli.dsl.script

import org.pipelinelabs.pipeline.pipe.dsl.PipelineDsl

public abstract class PipelineScript extends Script {

    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
