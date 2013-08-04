package nl.ikoodi.pipeline.runner;

import nl.ikoodi.pipeline.api.Pipeline;
import nl.ikoodi.pipeline.api.Stage;

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
