package org.pipelinelabs.pipeline.runner.dsl;

import org.pipelinelabs.pipeline.runner.messenger.internal.EmailContext;

import java.util.List;

public interface EmailDsl extends MessageContextDsl<EmailContext> {
    List<String> getTo();

    void to(String... to);

    void setTo(List<String> to);

    List<String> getCc();

    void cc(String... cc);

    void setCc(List<String> cc);

    EmailContext toContext();
}
