package org.pipelinelabs.pipeline.dsl.internal

import org.pipelinelabs.pipeline.messenger.Messenger
import org.pipelinelabs.pipeline.dsl.MessengerDsl

class DefaultMessengerDsl implements MessengerDsl {
    private final List<Messenger> messengers = []

    @Override
    void email(Closure config) {
        new DefaultEmailMessengerDsl().with {
            ConfigureUtil.configure(dsl, config)
            messengers << dsl.toMessenger()
        }
    }
}
