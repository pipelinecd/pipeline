package org.pipelinelabs.pipeline.dsl;

import groovy.lang.Closure;
import org.pipelinelabs.pipeline.messenger.MessageContext;

import java.util.List;

public interface AnnounceDsl {
    void email(Closure config);

    List<MessageContext> toContexts();
}
