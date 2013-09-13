package org.pipelinelabs.pipeline.dsl;

import java.util.List;

import groovy.lang.Closure;

import org.pipelinelabs.pipeline.messenger.MessageContext;

public interface AnnounceDsl {
    void email(Closure config);
    List<MessageContext> toContexts();
}
