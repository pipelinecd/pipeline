package cd.pipeline.messenger.internal;

import cd.pipeline.event.PipeEvent;
import cd.pipeline.messenger.MessageContext;
import cd.pipeline.messenger.Messenger;
import cd.pipeline.messenger.SpecializedMessenger;

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
