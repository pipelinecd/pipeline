package org.pipelinelabs.pipeline.messenger.internal;

import java.util.Collections;
import java.util.List;

import org.pipelinelabs.pipeline.messenger.MessageContext;

public class EmailContext implements MessageContext {
    private final String from;
    private final List<String> to;
    private final List<String> cc;

    public EmailContext(String from, List<String> to, List<String> cc) {
        this.from = from;
        this.to = Collections.unmodifiableList(to);
        this.cc = Collections.unmodifiableList(cc);
    }

    String getFrom() {
        return from;
    }

    List<String> getTo() {
        return to;
    }

    List<String> getCc() {
        return cc;
    }
}
