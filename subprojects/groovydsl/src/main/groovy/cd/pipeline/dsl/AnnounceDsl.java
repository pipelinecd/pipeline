package cd.pipeline.dsl;

import cd.pipeline.messenger.MessageContext;
import groovy.lang.Closure;

import java.util.List;

public interface AnnounceDsl {
    void email(Closure config);

    List<MessageContext> toContexts();
}
