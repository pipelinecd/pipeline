package nl.ikoodi.io.cy.dsl.model;

import groovy.lang.Closure;
import nl.ikoodi.io.cy.dsl.api.Pipeline;
import nl.ikoodi.io.cy.dsl.api.Stage;

public class DefaultPipeline implements Pipeline {

    @Override
    public Stage stage(final String name, final Closure closure) {
        final Stage stage = new DefaultStage(name);
        closure.setDelegate(stage);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
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
}
