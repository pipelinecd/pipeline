package org.pipelinelabs.pipeline.dsl;

import groovy.lang.Closure;
import org.pipelinelabs.pipeline.messenger.Messenger;

public interface MessengerDsl {
    void email(Closure config);

    Messenger toMessenger();
}
