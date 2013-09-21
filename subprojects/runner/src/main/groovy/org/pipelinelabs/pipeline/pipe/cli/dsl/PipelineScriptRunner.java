package org.pipelinelabs.pipeline.pipe.cli.dsl;

import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.pipelinelabs.pipeline.pipe.cli.dsl.script.PipelineScript;
import org.pipelinelabs.pipeline.pipe.dsl.PipelineDsl;
import org.pipelinelabs.pipeline.pipe.internal.ServiceRegistry;

import java.io.PrintStream;

public class PipelineScriptRunner {

    private final PipelineScript script;
    private final PrintStream redirectedOutput;
    private PrintStream originalStdOut;
    private PrintStream originalStdErr;

    public PipelineScriptRunner(final ServiceRegistry registry, final PrintStream output, final String scriptText) {
        redirectedOutput = output;
        final CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(PipelineScript.class.getName());

        final GroovyShell shell = new GroovyShell(config);
        script = (PipelineScript) shell.parse(scriptText);

        final PipelineDsl pipeline = registry.get(PipelineDsl.class);
        script.init(pipeline);
    }

    public void run() {
        try {
            startCapturingOutput();
            script.run();
        } finally {
            stopCapturingOutput();
        }
    }

    private void startCapturingOutput() {
        originalStdOut = System.out;
        System.setOut(redirectedOutput);
        originalStdErr = System.err;
        System.setErr(redirectedOutput);
    }

    private void stopCapturingOutput() {
        try {
            System.setOut(originalStdOut);
            System.setErr(originalStdErr);
            redirectedOutput.flush();
        } finally {
            originalStdOut = null;
            originalStdErr = null;
        }
    }
}
