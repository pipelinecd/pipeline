package nl.ikoodi.io.cy.builder;

import groovy.lang.Closure;
import groovy.lang.Script;
import nl.ikoodi.io.cy.builder.process.ProcessWrapper;

import java.util.Arrays;

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

    public boolean run(String command) throws Exception {
        final ProcessWrapper wrapper = new ProcessWrapper(Arrays.asList(command));
        echo("Command has terminated with status: " + wrapper.getStatus());
        echo("Output:\n" + wrapper.getInfos());
        echo("Error: " + wrapper.getErrors());
        if (wrapper.getStatus() == 0) {
            return true;
        }
        return false;

//        final ProcessBuilder processBuilder = new ProcessBuilder(command);
//        echo("Executing command [%s]%n", command);
//        final Process process = processBuilder.start();
//
//        if (0 == process.waitFor()) {
//            echo("Executing command [%s] SUCCEEDED%n", command);
//            return true;
//        }
//        return false;
    }

}
