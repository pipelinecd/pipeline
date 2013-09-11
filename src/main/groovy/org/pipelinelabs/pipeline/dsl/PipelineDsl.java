package org.pipelinelabs.pipeline.dsl;

import groovy.lang.Closure;

public interface PipelineDsl {
    StageDsl stage(String name, Closure closure);

    void echo(Object value);

    void echo(String format, Object... values);

    MessengerDsl messenger(Closure closure);

    AnnounceDsl announce(Closure closure);
}
