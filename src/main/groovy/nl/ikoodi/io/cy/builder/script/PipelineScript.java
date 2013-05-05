package nl.ikoodi.io.cy.builder.script;

import groovy.lang.Closure;
import groovy.lang.Script;
import nl.ikoodi.io.cy.api.Pipeline;
import nl.ikoodi.io.cy.api.Stage;

public abstract class PipelineScript extends Script implements Pipeline {

    private Pipeline pipeline;

    public void init(final Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public Stage stage(final String name, final Closure closure) {
        return pipeline.stage(name, closure);
    }

    @Override
    public void echo(final Object value) {
        pipeline.echo(value);
    }

    @Override
    public void echo(final String format, final Object... values) {
        pipeline.echo(format, values);
    }

}
