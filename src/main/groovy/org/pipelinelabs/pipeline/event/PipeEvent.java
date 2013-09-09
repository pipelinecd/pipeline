package org.pipelinelabs.pipeline.event;

public interface PipeEvent {
    String getName();
    String getStatus();
    String getDescription();
    String getDetails();
}
