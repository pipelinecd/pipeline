import org.testng.annotations.Test

class MainTest {

    @Test
    public void showHelp() {
        new Main().run(this, "-h");
    }
}
