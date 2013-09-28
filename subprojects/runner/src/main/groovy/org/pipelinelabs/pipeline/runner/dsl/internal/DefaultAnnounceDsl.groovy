package org.pipelinelabs.pipeline.runner.dsl.internal

import org.pipelinelabs.pipeline.runner.dsl.AnnounceDsl
import org.pipelinelabs.pipeline.runner.dsl.EmailDsl
import org.pipelinelabs.pipeline.runner.messenger.MessageContext
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
