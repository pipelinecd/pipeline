package nl.ikoodi.io.cy.builder;

import groovy.lang.Closure;
import groovy.lang.Script;

import java.io.IOException;

public abstract class BuildScript extends Script {

    public void stage(Object stage, Closure closure) {
        printf("executing stage %s\n", stage);
        closure.run();
    }

    public void echo(Object value) {
        println(value);
    }

    public void echo(String format, Object... values) {
        printf(format, values);
    }

    public boolean run(String command) {
        final ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            echo("Executing command [%s]%n", command);
            processBuilder.start();
        } catch (IOException e) {
            echo("Executing command [%s] FAILED!%nBecause of %s", command, e.toString());
            return false;
        }
        echo("Executing command [%s] SUCCEEDED%n", command);
        return false;
    }

}
