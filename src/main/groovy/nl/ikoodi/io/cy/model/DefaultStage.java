package nl.ikoodi.io.cy.model;

import nl.ikoodi.io.cy.api.Stage;
import org.apache.maven.shared.utils.cli.*;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

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

//    @Override
//    @Deprecated
//    public void echo(final String format, final Object... values) {
//        System.out.printf(format, values);
//    }

    @Override
    public void run(final String command) throws Exception {
        final Commandline cl = new Commandline(command);

        System.out.println();
        final WriterStreamConsumer stdOut = new WriterStreamConsumer(new BufferedWriter(new OutputStreamWriter(System.out)));
        final WriterStreamConsumer stdErr = new WriterStreamConsumer(new BufferedWriter(new OutputStreamWriter(System.err)));
        final int exitStatus = CommandLineUtils.executeCommandLine(cl, stdOut, stdErr);
        if (0 != exitStatus) {
            final String msg = "Error occurred during execution of command [%s]";
            throw new RuntimeException(String.format(msg, command));
        }
    }

}
