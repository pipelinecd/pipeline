package org.pipelinelabs.pipeline.runner.dsl;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

public interface PipelineDsl {
    StageDsl stage(String name, Closure closure);

    void echo(Object value);

    void echo(String format, Object... values);

    MessengerDsl messenger(@DelegatesTo(MessengerDsl.class) Closure closure);

    AnnounceDsl announce(@DelegatesTo(AnnounceDsl.class) Closure closure);
}
