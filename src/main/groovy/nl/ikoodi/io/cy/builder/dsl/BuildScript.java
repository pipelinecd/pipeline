package nl.ikoodi.io.cy.builder.dsl;

import groovy.lang.Closure;
import groovy.lang.Script;

public abstract class BuildScript extends Script {

    public void stage(final Object stage, final Closure closure) {
//        printf("executing stage %s\n", stage);
        closure.run();
    }

    public void echo(final Object value) {
        print(value);
    }

    public void echo(final String format, final Object... values) {
        printf(format, values);
    }

    public boolean run(final String command) throws Exception {
        final ProcessBuilder processBuilder = new ProcessBuilder(command);
        final Process process = processBuilder.inheritIO().start();
        if (0 == process.waitFor()) {
            return true;
        }
        return false;
    }

}
