package org.pipelinelabs.pipeline.runner.dsl;

import org.pipelinelabs.pipeline.runner.messenger.MessageContext;

public interface MessageContextDsl<T extends MessageContext> {
    T toContext();
}
