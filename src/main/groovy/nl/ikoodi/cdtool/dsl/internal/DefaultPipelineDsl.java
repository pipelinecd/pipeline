package nl.ikoodi.cdtool.dsl.internal;

import groovy.lang.Closure;
import nl.ikoodi.cdtool.api.Pipeline;
import nl.ikoodi.cdtool.api.Stage;
import nl.ikoodi.cdtool.dsl.PipelineDsl;
import nl.ikoodi.cdtool.dsl.StageDsl;
import nl.ikoodi.cdtool.runner.DefaultPipeline;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultPipelineDsl implements PipelineDsl, DslExporter<Pipeline> {

    private Set<StageDsl> stages = new LinkedHashSet<>();

    @Override
    public StageDsl stage(final String name, final Closure closure) {
        final StageDsl stage = new DefaultStageDsl(name);
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
        final DefaultPipeline pipeline = new DefaultPipeline();
        for (StageDsl stage : stages) {
            pipeline.add(((DefaultStageDsl) stage).export());
        }
        return pipeline;
    }
}
