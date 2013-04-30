package nl.ikoodi.io.cy.api;

import groovy.lang.Closure;

public interface Pipeline {

    Stage stage(String name, Closure closure);

}
