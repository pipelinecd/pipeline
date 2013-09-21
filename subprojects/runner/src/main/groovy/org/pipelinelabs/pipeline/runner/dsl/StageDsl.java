package org.pipelinelabs.pipeline.runner.dsl;

public interface StageDsl {

    String getName();

    String getDescription();

    void setDescription(String description);

    void run(String command) throws Exception;
}
