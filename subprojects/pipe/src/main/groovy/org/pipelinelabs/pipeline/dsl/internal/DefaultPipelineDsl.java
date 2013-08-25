package org.pipelinelabs.pipeline.dsl.internal;

import groovy.lang.Closure;
import org.pipelinelabs.pipeline.api.Pipeline;
import org.pipelinelabs.pipeline.dsl.StageDsl;
import org.pipelinelabs.pipeline.runner.DefaultPipeline;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultPipelineDsl implements InternalPipelineDsl {

    private Set<InternalStageDsl> stages = new LinkedHashSet<>();

    @Override
    public StageDsl stage(final String name, final Closure closure) {
        final InternalStageDsl stage = new DefaultStageDsl(name);
        closure.setDelegate(stage);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();

        stages.add(stage);
        return stage;
    }

    @Override
    public void echo(final Object value) {
        System.out.print(value);
    }

    @Override
    public void echo(final String format, final Object... values) {
        System.out.printf(format, values);
    }

    @Override
    public Pipeline export() {
        final Pipeline pipeline = new DefaultPipeline();
        for (InternalStageDsl stage : stages) {
            pipeline.add(stage.export());
        }
        return pipeline;
    }
}
