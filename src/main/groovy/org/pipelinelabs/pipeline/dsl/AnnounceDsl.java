package org.pipelinelabs.pipeline.dsl;

import groovy.lang.Closure;

public interface AnnounceDsl {
    void email(Closure config);
}
