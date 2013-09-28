package org.pipelinelabs.pipeline.runner.messenger;

import org.pipelinelabs.pipeline.runner.event.PipeEvent;

public interface Messenger {
    void process(MessageContext context, PipeEvent event);
}
