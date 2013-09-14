package org.pipelinelabs.pipeline.messenger.internal;

import org.pipelinelabs.pipeline.messenger.Messenger;
import org.pipelinelabs.pipeline.messenger.SpecializedMessenger;
import org.pipelinelabs.pipeline.messenger.MessageContext;
import org.pipelinelabs.pipeline.event.PipeEvent;

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
