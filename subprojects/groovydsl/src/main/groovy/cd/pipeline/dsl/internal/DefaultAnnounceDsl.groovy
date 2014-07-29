package cd.pipeline.dsl.internal

import cd.pipeline.dsl.AnnounceDsl
import cd.pipeline.dsl.EmailDsl
import cd.pipeline.messenger.MessageContext
import cd.pipeline.util.ConfigureUtil

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
