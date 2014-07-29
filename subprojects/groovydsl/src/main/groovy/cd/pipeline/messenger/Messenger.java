package cd.pipeline.messenger;

import cd.pipeline.event.PipeEvent;

public interface Messenger {
    void process(MessageContext context, PipeEvent event);
}
