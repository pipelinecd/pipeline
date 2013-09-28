package org.pipelinelabs.pipeline.runner.messenger;

public interface SpecializedMessenger extends Messenger {
    boolean accepts(MessageContext context);
}
