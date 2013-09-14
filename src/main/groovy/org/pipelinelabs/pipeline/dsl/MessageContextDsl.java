package org.pipelinelabs.pipeline.dsl;

import org.pipelinelabs.pipeline.messenger.MessageContext;

public interface MessageContextDsl<T extends MessageContext> {
    T toContext();
}
