package nl.ikoodi.io.cy.cli.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.ArrayList;
import java.util.List;

@Parameters(
        commandNames = {FeedbackCommand.CMD_NAME}
        , commandDescription = "Give feedback about your experience with this tool"
)
public class FeedbackCommand {
    public static final String CMD_NAME = "feedback";

//    @Parameter(
//            description = "Your feedback message"
//            , required = true
//            , arity = 1
//    )
//    private List<String> messages = new ArrayList<String>();

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

//    public List<String> getMessages() {
//        return messages;
//    }

    public String getPositive() {
        return positive;
    }

    public String getNegative() {
        return negative;
    }
}
