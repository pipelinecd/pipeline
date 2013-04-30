package nl.ikoodi.io.cy.model;

import groovy.lang.Closure;
import nl.ikoodi.io.cy.api.Pipeline;
import nl.ikoodi.io.cy.api.Stage;

public class DefaultPipeline implements Pipeline {

    @Override
    public Stage stage(final String name, final Closure closure) {
        final Stage stage = new DefaultStage(name);
        closure.setDelegate(stage);
        closure.call();
        return stage;
    }
}
