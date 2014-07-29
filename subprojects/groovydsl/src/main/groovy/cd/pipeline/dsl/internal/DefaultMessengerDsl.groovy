package cd.pipeline.dsl.internal

import cd.pipeline.dsl.EmailMessengerDsl
import cd.pipeline.dsl.MessengerDsl
import cd.pipeline.messenger.Messenger
import cd.pipeline.messenger.internal.AggregateMessenger
import cd.pipeline.util.ConfigureUtil

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
