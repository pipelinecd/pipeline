package org.pipelinelabs.pipeline.dsl;

import java.util.List;
import org.pipelinelabs.pipeline.messenger.internal.EmailContext;

public interface EmailDsl {
    List<String> getTo();
    void to(String... to);
    void setTo(List<String> to);
    List<String> getCc();
    void cc(String... cc);
    void setCc(List<String> cc);
    EmailContext toContext();
}
