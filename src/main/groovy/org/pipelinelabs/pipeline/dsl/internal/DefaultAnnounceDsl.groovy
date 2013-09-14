package org.pipelinelabs.pipeline.dsl.internal

import org.pipelinelabs.pipeline.dsl.AnnounceDsl
import org.pipelinelabs.pipeline.dsl.EmailDsl
import org.pipelinelabs.pipeline.messenger.MessageContext
import org.pipelinelabs.pipeline.util.ConfigureUtil

class DefaultAnnounceDsl implements AnnounceDsl {
    final EmailDsl email = new DefaultEmailDsl()

    @Override
    void email(Closure config) {
        ConfigureUtil.configure(email, config)
    }

    List<MessageContext> toContexts() {
        return [email].collect { it.toContext() }
    }
}
