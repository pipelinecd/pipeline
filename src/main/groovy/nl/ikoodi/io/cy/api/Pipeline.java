package nl.ikoodi.io.cy.api;

import groovy.lang.Closure;

public interface Pipeline {

    void stage(String name, Closure closure);

}
