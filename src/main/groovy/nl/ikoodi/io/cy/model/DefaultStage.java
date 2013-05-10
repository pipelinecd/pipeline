package nl.ikoodi.io.cy.model;

import nl.ikoodi.io.cy.api.Stage;
import org.apache.maven.shared.utils.cli.CommandLineException;
import org.apache.maven.shared.utils.cli.CommandLineUtils;
import org.apache.maven.shared.utils.cli.Commandline;
import org.apache.maven.shared.utils.cli.WriterStreamConsumer;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Properties;

public class DefaultStage implements Stage {
    private final String name;
    private String description;

    public DefaultStage(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public void run(final String command) throws Exception {
        final ExternalProcess process = new ExternalProcess();
        process.setCommand(command);
        process.run();
    }

    public class ExternalProcess {

        private PrintStream out = System.out;
        private PrintStream err = System.err;
        private String command;
        private Properties environmentVariables = new Properties();

        public String getCommand() {
            return command;
        }

        public void setCommand(final String command) {
            this.command = command;
        }

        public Properties getEnvironmentVariables() {
            return environmentVariables;
        }

        public void setEnvironmentVariables(Properties environmentVariables) {
            this.environmentVariables = environmentVariables;
        }

        public void run() {
            out.println();
            final Commandline cl = new Commandline(command);
            final int exitStatus = executeCommand(cl, createStreamingConsumer(out), createStreamingConsumer(err));
            if (0 != exitStatus) {
                final String msg = "Error occurred during execution of command [%s]";
                throw new RuntimeException(String.format(msg, command));
            }
        }

        private int executeCommand(final Commandline cl,
                                   final WriterStreamConsumer stdOut, final WriterStreamConsumer stdErr) {
            final int exitStatus;
            try {
                exitStatus = CommandLineUtils.executeCommandLine(cl, stdOut, stdErr);
            } catch (CommandLineException e) {
                throw new RuntimeException("Failed to execute command", e);
            }
            return exitStatus;
        }

        private WriterStreamConsumer createStreamingConsumer(final PrintStream stream) {
            return new WriterStreamConsumer(new BufferedWriter(new OutputStreamWriter(stream)));
        }
    }

}
