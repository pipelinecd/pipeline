package cd.pipeline.dsl;

import cd.pipeline.messenger.MessageContext;

public interface MessageContextDsl<T extends MessageContext> {
    T toContext();
}
