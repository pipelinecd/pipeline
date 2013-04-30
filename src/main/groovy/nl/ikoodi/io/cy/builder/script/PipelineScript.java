package nl.ikoodi.io.cy.builder.script;

import groovy.lang.Closure;
import groovy.lang.Script;
import nl.ikoodi.io.cy.api.Pipeline;
import nl.ikoodi.io.cy.api.Stage;
import org.apache.maven.shared.utils.cli.CommandLineUtils;
import org.apache.maven.shared.utils.cli.Commandline;

public abstract class PipelineScript extends Script implements Pipeline {

    private Pipeline pipeline;

    public void init(final Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public Stage stage(final String name, final Closure closure) {
        return pipeline.stage(name, closure);
    }

    public void echo(final Object value) {
        print(value);
    }

    public void echo(final String format, final Object... values) {
        printf(format, values);
    }

    public void run(final String command) throws Exception {
        final Commandline cl = new Commandline(command);
        final CommandLineUtils.StringStreamConsumer stdOut = new CommandLineUtils.StringStreamConsumer();
        final CommandLineUtils.StringStreamConsumer stdErr = new CommandLineUtils.StringStreamConsumer();
        final int exitStatus = CommandLineUtils.executeCommandLine(cl, stdOut, stdErr);
        System.out.print(stdOut.getOutput());
        System.err.print(stdErr.getOutput());
        if (0 != exitStatus) {
            final String msg = "Error occurred during execution of command [%s]";
            throw new RuntimeException(String.format(msg, command));
        }
    }
}
