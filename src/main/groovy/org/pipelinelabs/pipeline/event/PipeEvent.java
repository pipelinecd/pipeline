package org.pipelinelabs.pipeline.event;

public class PipeEvent {
    private final String name;
    private final String status;
    private final String description;
    private final String details;

    public PipeEvent(String name, String status, String description, String details) {
        this.name = name;
        this.status = status;
        this.description = description;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getDetails() {
        return details;
    }
}
