package nl.ikoodi.io.cy.builder;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BuildScriptRunner {

    private final Script script;

    public BuildScriptRunner(final PrintStream output, final String scriptText) {
        this(new PrintWriter(output, true), scriptText);
    }

    public BuildScriptRunner(final PrintWriter output, final String scriptText) {
        final CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(BuildScript.class.getName());
        config.setOutput(output);

        final GroovyShell shell = new GroovyShell(config);

        script = shell.parse(scriptText);
        script.setProperty("out", output);
    }

    public void run() {
        script.run();
    }
}
