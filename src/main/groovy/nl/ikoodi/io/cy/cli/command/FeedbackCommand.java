package nl.ikoodi.io.cy.cli.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.PrintStream;

@Parameters(
        commandNames = {FeedbackCommand.NAME}
        , commandDescription = "Give feedback about your experience with this tool"
)
public class FeedbackCommand implements Command {
    public static final String NAME = "feedback";

    @Parameter(
            names = {
                    "-p"
                    , "--positive"
            }
            , description = "Your positive feedback message"
    )
    private String positive;

    @Parameter(
            names = {
                    "-n"
                    , "--negative"
            }
            , description = "Your negative feedback message"
    )
    private String negative;

    private PrintStream outputConsumer;

    public FeedbackCommand(final PrintStream outputConsumer) {
        this.outputConsumer = outputConsumer;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public String getPositive() {
        return positive;
    }

    public String getNegative() {
        return negative;
    }

    @Override
    public boolean canHandle(final String commandName) {
        return getName().equals(commandName);
    }

    @Override
    public void handle() {
        String feedbackType;
        String msg;
        if (negative != null && !negative.isEmpty()) {
            feedbackType = "negative";
            msg = negative;
        } else if (positive != null && !positive.isEmpty()) {
            feedbackType = "positive";
            msg = positive;
        } else {
            return;
        }
        outputConsumer.printf("Thnx, received your %s feedback: %s\n", feedbackType, msg);
    }
}
