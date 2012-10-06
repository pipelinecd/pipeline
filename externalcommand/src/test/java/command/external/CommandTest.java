package command.external;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CommandTest {

    @Test
    public void runCommand() {
        final Command command = new Command();
        try {
            final int exitCode = command.run();
            assertEquals(exitCode, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
