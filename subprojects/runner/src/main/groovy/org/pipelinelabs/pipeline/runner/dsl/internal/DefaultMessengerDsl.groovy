package org.pipelinelabs.pipeline.runner.dsl.internal

import org.pipelinelabs.pipeline.runner.dsl.EmailMessengerDsl
import org.pipelinelabs.pipeline.runner.dsl.MessengerDsl
import org.pipelinelabs.pipeline.runner.messenger.Messenger
import org.pipelinelabs.pipeline.runner.messenger.internal.AggregateMessenger
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
