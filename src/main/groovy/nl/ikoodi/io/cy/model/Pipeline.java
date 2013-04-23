package nl.ikoodi.io.cy.model;

import groovy.lang.Closure;

public class Pipeline implements nl.ikoodi.io.cy.api.Pipeline {

    @Override
    public void stage(final String name, final Closure closure) {
//        System.out.println(String.format("Configuring Stage [%s]", name));
        closure.run();
    }
}
