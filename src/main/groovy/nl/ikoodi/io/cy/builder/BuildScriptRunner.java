package nl.ikoodi.io.cy.builder;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.PrintWriter;

public class BuildScriptRunner {

    private final Script script;

    public BuildScriptRunner(final PrintWriter output, final String scriptText) {
        final CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(BuildScript.class.getName());
        config.setOutput(output);

        final GroovyShell shell = new GroovyShell(config);

        script = shell.parse(scriptText);
        script.setProperty("out", output);

//        final GroovyCodeSource source = new GroovyCodeSource(scriptText, "build", "");
    }

    public void run() {
        script.run();
    }
}
