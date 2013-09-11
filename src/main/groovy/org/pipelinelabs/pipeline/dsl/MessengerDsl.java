package org.pipelinelabs.pipeline.dsl;

import groovy.lang.Closure;

public interface MessengerDsl {
    void email(Closure config);
}
