package nl.ikoodi.cdtool.dsl.internal;

import nl.ikoodi.cdtool.api.Stage;
import nl.ikoodi.cdtool.dsl.StageDsl;
import nl.ikoodi.cdtool.dsl.TaskDsl;
import nl.ikoodi.cdtool.runner.DefaultStage;
import org.apache.maven.shared.utils.cli.CommandLineException;
import org.apache.maven.shared.utils.cli.CommandLineUtils;
import org.apache.maven.shared.utils.cli.Commandline;
import org.apache.maven.shared.utils.cli.WriterStreamConsumer;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class DefaultStageDsl implements StageDsl, DslExporter<Stage> {
    private final String name;
    private String description;
    private List<TaskDsl> tasks = new LinkedList<>();

    public DefaultStageDsl(final String name) {
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
        tasks.add(new ShellCommandTaskDsl(command));

        final ExternalProcess process = new ExternalProcess();
        process.setCommand(command);
        process.run();
    }

    @Override
    public Stage export() {
        Stage stage = new DefaultStage();
        for (TaskDsl task : tasks) {
            stage.add(((ShellCommandTaskDsl)task).export());
        }

        return null;
    }

    private class ExternalProcess {

        private PrintStream out = System.out;
        private PrintStream err = System.err;
        private String command;

        public void setCommand(final String command) {
            this.command = command;
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
