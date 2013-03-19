package nl.ikoodi.io.cy.builder;

import groovy.lang.Closure;
import groovy.lang.Script;
import nl.ikoodi.io.cy.builder.process.ProcessWrapper;

import java.util.Arrays;

public abstract class BuildScript extends Script {

    public void stage(final Object stage, final Closure closure) {
        printf("executing stage %s\n", stage);
        closure.run();
    }

    public void echo(final Object value) {
        print(value);
//        println(value);
    }

    public void echo(final String format, final Object... values) {
        printf(format, values);
    }

    public boolean run(final String command) throws Exception {
//        final ProcessWrapper wrapper = new ProcessWrapper(Arrays.asList(command));
//        if (wrapper.getExitStatus() == 0) {
//            return true;
//        }
//        return false;

        final ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();
//        echo("Executing command [%s]%n", command);
//        final Process process = processBuilder.inheritIO().start();
        final Process process = processBuilder.start();
        if (0 == process.waitFor()) {
//            echo(process.getOutputStream().toString());
//            echo("Executing command [%s] SUCCEEDED%n", command);
            return true;
        }
//        echo(process.getOutputStream().toString());
        return false;
    }
}
