package org.pipelinelabs.pipeline.pipe.api;

import java.util.Set;

public interface Pipeline {

    void add(Stage stage);

    Set<Stage> getStages();
}
