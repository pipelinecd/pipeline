package nl.ikoodi.io.cy.cli.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

import java.io.PrintStream;

@Parameters(
        commandNames = {HelpCommand.NAME}
        , commandDescription = "Show help information about a command"
)
public class HelpCommand implements Command {
    public static final String NAME = "help";

    private final PrintStream outputConsumer;
    private final JCommander cli;

    public HelpCommand(final PrintStream outputConsumer, final JCommander cli) {
        this.outputConsumer = outputConsumer;
        this.cli = cli;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean canHandle(final String commandName) {
        return getName().equals(commandName);
    }

    @Override
    public void handle() {
        outputUsage();
    }

    public void outputUsage() {
        outputUsage(outputConsumer);
    }

    private void outputUsage(final PrintStream output) {
        final StringBuilder stringOutput = new StringBuilder();
        cli.usage(stringOutput);
        output.print(stringOutput);
    }
}
