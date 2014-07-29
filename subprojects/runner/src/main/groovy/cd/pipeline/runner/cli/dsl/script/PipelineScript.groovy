package cd.pipeline.runner.cli.dsl.script

import cd.pipeline.dsl.PipelineDsl

public abstract class PipelineScript extends Script {
    @Delegate
    private PipelineDsl pipeline;

    public void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline;
    }
}
