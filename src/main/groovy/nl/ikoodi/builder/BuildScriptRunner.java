package nl.ikoodi.builder;

import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

public class BuildScriptRunner {

    private final Script script;

    public BuildScriptRunner(final String scriptText) {
        final CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(BuildScript.class.getName());

        final GroovyShell shell = new GroovyShell(config);

        script = shell.parse(scriptText);

        final GroovyCodeSource source = new GroovyCodeSource(scriptText, "build", "");
    }

    public void run() {
        script.run();
    }
}
