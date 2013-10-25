package org.pipelinelabs.pipeline.dsl;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.pipelinelabs.pipeline.messenger.Messenger;

public interface MessengerDsl {
    void email(@DelegatesTo(EmailDsl.class) Closure config);

    Messenger toMessenger();
}
