package nl.ikoodi.pipeline.dsl;

import groovy.lang.Closure;

public interface PipelineDsl {

    StageDsl stage(String name, Closure closure);

    void echo(Object value);

    void echo(String format, Object... values);
}
