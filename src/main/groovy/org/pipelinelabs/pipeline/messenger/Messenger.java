package org.pipelinelabs.pipeline.messenger;

import org.pipelinelabs.pipeline.event.PipeEvent;

public interface Messenger {
    void process(MessageContext context, PipeEvent event);
}
