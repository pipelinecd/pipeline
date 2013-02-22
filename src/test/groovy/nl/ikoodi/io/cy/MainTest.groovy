package nl.ikoodi.io.cy

import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.equalTo

public class MainTest {

    private ByteArrayOutputStream output

    @BeforeMethod
    public void setUp() {
        output = new ByteArrayOutputStream()
    }

    @DataProvider(name = 'differentWaysToOutputUsageInfo')
    private Object[][] differentWaysToOutputHelp() {
        [
                [[] as String[]],
                [['-h'] as String[]],
                [['--help'] as String[]],
                [['help'] as String[]],
        ] as Object[][]
    }

    @Test(dataProvider = 'differentWaysToOutputUsageInfo')
    void outputsUsageInfo(args) {
        final main = getTestableMain()
        main.run(this, args)

        assertThat(output.toString(), containsString('--help, -h'))
        assertThat(output.toString(), containsString('help      Show help information about a command'))
    }

    @DataProvider(name = 'differentWaysToGivePositiveFeedback')
    public Object[][] differentWaysToGivePositiveFeedback() {
        [
                [['feedback', '-p', 'did good'] as String[]],
                [['feedback', '--positive', 'did good'] as String[]],
        ] as Object[][]
    }

    @Test(dataProvider = 'differentWaysToGivePositiveFeedback')
    public void canGivePositiveFeedback(args) {
        final main = getTestableMain()
        main.run(this, args)

        assertThat(output.toString(), equalTo('Thnx, received your positive feedback: did good\n'))
    }

    @DataProvider(name = 'differentWaysToGiveNegativeFeedback')
    public Object[][] differentWaysToGiveNegativeFeedback() {
        [
                [['feedback', '-n', 'did bad'] as String[]],
                [['feedback', '--negative', 'did bad'] as String[]],
        ] as Object[][]
    }

    @Test(dataProvider = 'differentWaysToGiveNegativeFeedback')
    public void canGiveNegativeFeedback(args) {
        final main = getTestableMain()
        main.run(this, args)

        assertThat(output.toString(), equalTo('Thnx, received your negative feedback: did bad\n'))
    }

    private Main getTestableMain() {
        def main = new Main()
        main.outputConsumer = new PrintStream(output)
        main.errorConsumer = new PrintStream(output)
        return main
    }
}
