package nl.ikoodi.io.cy

import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import nl.ikoodi.io.cy.cli.command.*

class Main {
    def PrintStream outputConsumer = System.out
    def PrintStream errorConsumer = System.err

    def run(scriptName, String... args) {
        final mainOptions = new MainCommand()
        final JCommander cli = createCommander(mainOptions)
        final helpCmd = new HelpCommand(outputConsumer, cli)
        final List<Command> commands = new ArrayList<>()
        commands.add(helpCmd)
        commands.addAll(createCommands())
        commands.each { cmd ->
            cli.addCommand(cmd)
        }

        final cmdName
        try {
            cli.parse(args)
            cmdName = cli.getParsedCommand()
            handleCommand(mainOptions, helpCmd, commands, cmdName)
        } catch (ParameterException e) {
            errorConsumer.println(e.getMessage())
            helpCmd.outputUsage(errorConsumer)
        }
    }

    private void handleCommand(final MainCommand mainOptions, final HelpCommand helpCmd,
                               final List<Command> commands, final String cmdName) {
        if (mainOptions.help) {
            helpCmd.outputUsage()
            return
        }
        final Command cmd = commands.find { it.canHandle(cmdName) }
        if (cmd) {
            cmd.handle()
            return
        }
        helpCmd.outputUsage(errorConsumer)
    }

    private List<Command> createCommands() {
        final List<Command> commands = new ArrayList<>()
        commands.add(new RunCommand(outputConsumer))
        commands.add(new FeedbackCommand(outputConsumer))
        return commands
    }

    private JCommander createCommander(final MainCommand mainOptions) {
        final cli = new JCommander(mainOptions);
        cli.with {
            programName = 'beam'
            allowAbbreviatedOptions = true
        }
        return cli
    }
}
