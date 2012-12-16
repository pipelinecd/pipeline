import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class MainTest {

    @Test
    public void showHelp() {
        new Main().run(this, "-h");
    }
}
