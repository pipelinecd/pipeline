package org.pipelinelabs.pipeline.dsl.internal

import org.pipelinelabs.pipeline.dsl.EmailMessengerDsl
import org.pipelinelabs.pipeline.dsl.MessengerDsl
import org.pipelinelabs.pipeline.messenger.Messenger
import org.pipelinelabs.pipeline.messenger.internal.AggregateMessenger
import org.pipelinelabs.pipeline.util.ConfigureUtil

class DefaultMessengerDsl implements MessengerDsl {
    final EmailMessengerDsl email = new DefaultEmailMessengerDsl()

    @Override
    void email(Closure config) {
        ConfigureUtil.configure(email, config)
    }

    @Override
    Messenger toMessenger() {
        return new AggregateMessenger([email].collect { it.toMessenger() });
    }
}
