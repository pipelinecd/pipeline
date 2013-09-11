package org.pipelinelabs.pipeline.dsl.internal

import org.pipelinelabs.pipeline.dsl.AnnounceDsl
import org.pipelinelabs.pipeline.dsl.internal.DefaultEmailDsl
import org.pipelinelabs.pipeline.messenger.MessageContext

class DefaultAnnounceDsl implements AnnounceDsl {
    private final List<MessageContext> contexts = []

    @Override
    void email(Closure config) {
        new DefaultEmailDsl().with {
            ConfigureUtil.configure(dsl, config)
            contexts << dsl.toContext()
        }
    }
}
