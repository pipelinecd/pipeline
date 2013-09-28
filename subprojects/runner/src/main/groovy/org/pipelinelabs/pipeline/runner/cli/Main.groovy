package org.pipelinelabs.pipeline.runner.cli

import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import org.pipelinelabs.pipeline.runner.cli.command.*
import org.pipelinelabs.pipeline.runner.internal.ServiceRegistry

class Main {
    static final int EXIT_FAILURE = 1
    static final int EXIT_SUCCESS = 0
    PrintStream outputConsumer = System.out
    PrintStream errorConsumer = System.err

    int run(String programName, String... args) {
        final mainOptions = new MainCommand()
        final JCommander cli = createCommander(programName, mainOptions)
        final helpCmd = new HelpCommand(errorConsumer, cli)
        final List<Command> commands = new ArrayList<>()
        commands.add(helpCmd)
        commands.addAll(createCommands())
        commands.each { cmd ->
            cli.addCommand(cmd)
        }

        final cmdName
        final handledCommand
        try {
            cli.parse(args)
            cmdName = cli.getParsedCommand()
            handledCommand = handleCommand(mainOptions, helpCmd, commands, cmdName)
        } catch (ParameterException e) {
            errorConsumer.println(e.getMessage())
            helpCmd.outputUsage()
            return EXIT_FAILURE
        }
        if (handledCommand) {
            return EXIT_SUCCESS
        }
        return EXIT_FAILURE
    }

    private boolean handleCommand(final MainCommand mainOptions, final HelpCommand helpCmd,
                                  final List<Command> commands, final String cmdName) {
        if (mainOptions.help) {
            helpCmd.outputUsage()
            return false
        }
        final Command cmd = commands.find { it.canHandle(cmdName) }
        if (cmd) {
            cmd.handle()
            return true
        }
        helpCmd.outputUsage()
        return false
    }

    private List<Command> createCommands() {
        final List<Command> commands = new ArrayList<>()
        commands.add(new RunCommand(outputConsumer, getRegistry()))
        commands.add(new FeedbackCommand(outputConsumer))
        return commands
    }

    private JCommander createCommander(final String name, final MainCommand mainOptions) {
        final cli = new JCommander(mainOptions);
        cli.with {
            programName = name
            allowAbbreviatedOptions = true
        }
        return cli
    }

    private ServiceRegistry getRegistry() {
        return new ServiceLocator().find(ServiceRegistry)
    }
}
