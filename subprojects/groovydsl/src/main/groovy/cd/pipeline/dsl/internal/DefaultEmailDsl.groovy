package cd.pipeline.dsl.internal

import cd.pipeline.dsl.EmailDsl
import cd.pipeline.messenger.internal.EmailContext

class DefaultEmailDsl implements EmailDsl {
    List<String> to = []
    List<String> cc = []

    @Override
    void to(String... to) {
        this.to.addAll(to)
    }

    @Override
    void cc(String... cc) {
        this.cc.addAll(cc)
    }

    EmailContext toContext() {
        return new EmailContext(to, cc)
    }
}
