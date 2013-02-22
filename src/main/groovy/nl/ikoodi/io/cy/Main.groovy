package nl.ikoodi.io.cy

import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import nl.ikoodi.io.cy.cli.command.FeedbackCommand
import nl.ikoodi.io.cy.cli.command.HelpCommand
import nl.ikoodi.io.cy.cli.command.MainCommand

class Main {
    def PrintStream outputConsumer = System.out
    def PrintStream errorConsumer = System.err

    def run(scriptName, String... args) {
        final mainOptions = new MainCommand()
        final helpCmd = new HelpCommand()
        final feedbackCmd = new FeedbackCommand()

        final cli = new JCommander(mainOptions);
        cli.setProgramName('beam')
        cli.allowAbbreviatedOptions = true
        cli.addCommand(helpCmd)
        cli.addCommand(feedbackCmd)

        final cmd
        try {
            cli.parse(args)
            cmd = cli.getParsedCommand()
        } catch (ParameterException e) {
            errorConsumer.println(e.getMessage())
            outputUsage(cli)
            return
        }

        if (HelpCommand.CMD_NAME.equals(cmd) || mainOptions.help) {
            outputUsage(cli)
            return
        }

        if (FeedbackCommand.CMD_NAME.equals(cmd)) {
            def feedbackType;
            def msg;
            if (feedbackCmd.negative) {
                feedbackType = 'negative'
                msg = feedbackCmd.negative;
            } else if (feedbackCmd.positive) {
                feedbackType = 'positive'
                msg = feedbackCmd.positive;
            } else {
                outputUsage(cli, cmd)
                return
            }
            outputConsumer.printf('Thnx, received your %s feedback: %s\n', feedbackType, msg)
            return
        }

        outputUsage(cli)
    }

    private void outputUsage(final JCommander cli) {
        final output = new StringBuilder()
        cli.usage(output)
        errorConsumer.print(output);
    }

    private void outputUsage(final JCommander cli, final String commandName) {
        final output = new StringBuilder()
        cli.usage(commandName, output)
        errorConsumer.print(output);
    }

    void setOutputConsumer(final PrintStream consumer) {
        this.outputConsumer = consumer
    }

    void setErrorConsumer(final PrintStream errorConsumer) {
        this.errorConsumer = errorConsumer
    }
}
