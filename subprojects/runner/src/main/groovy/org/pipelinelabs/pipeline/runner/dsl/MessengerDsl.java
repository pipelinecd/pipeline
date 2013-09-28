package org.pipelinelabs.pipeline.runner.dsl;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.pipelinelabs.pipeline.runner.messenger.Messenger;

public interface MessengerDsl {
    void email(@DelegatesTo(EmailDsl.class) Closure config);

    Messenger toMessenger();
}
