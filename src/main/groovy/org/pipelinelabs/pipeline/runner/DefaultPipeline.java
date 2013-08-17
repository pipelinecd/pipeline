package org.pipelinelabs.pipeline.runner;

import org.pipelinelabs.pipeline.api.Pipeline;
import org.pipelinelabs.pipeline.api.Stage;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultPipeline implements Pipeline {

    private Set<Stage> stages = new LinkedHashSet<>();

    @Override
    public void add(final Stage stage) {
        stages.add(stage);
    }

    @Override
    public Set<Stage> getStages() {
        return stages;
    }
}
