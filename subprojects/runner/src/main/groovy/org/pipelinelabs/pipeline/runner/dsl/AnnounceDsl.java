package org.pipelinelabs.pipeline.runner.dsl;

import groovy.lang.Closure;
import org.pipelinelabs.pipeline.runner.messenger.MessageContext;

import java.util.List;

public interface AnnounceDsl {
    void email(Closure config);

    List<MessageContext> toContexts();
}
