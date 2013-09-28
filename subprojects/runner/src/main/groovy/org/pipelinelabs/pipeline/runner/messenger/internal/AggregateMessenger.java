package org.pipelinelabs.pipeline.runner.messenger.internal;

import org.pipelinelabs.pipeline.runner.event.PipeEvent;
import org.pipelinelabs.pipeline.runner.messenger.MessageContext;
import org.pipelinelabs.pipeline.runner.messenger.Messenger;
import org.pipelinelabs.pipeline.runner.messenger.SpecializedMessenger;

public class AggregateMessenger implements Messenger {
    private final Iterable<? extends SpecializedMessenger> messengers;

    public AggregateMessenger(Iterable<? extends SpecializedMessenger> messengers) {
        this.messengers = messengers;
    }

    public void process(MessageContext context, PipeEvent event) {
        for (SpecializedMessenger messenger : messengers) {
            if (messenger.accepts(context)) {
                messenger.process(context, event);
            }
        }
    }
}
