package cd.pipeline.dsl;

import cd.pipeline.messenger.Messenger;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

public interface MessengerDsl {
    void email(@DelegatesTo(EmailDsl.class) Closure config);

    Messenger toMessenger();
}
