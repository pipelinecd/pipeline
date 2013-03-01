package nl.ikoodi.io.cy.builder;

import groovy.lang.Closure;
import groovy.lang.Script;

public abstract class BuildScript extends Script {

    public void stage(Object stage, Closure closure) {
        printf("executing stage %s\n", stage);
        closure.run();
    }

    public void echo(Object value) {
        println(value);
    }
}
