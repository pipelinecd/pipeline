package nl.ikoodi.cdtool.cli

import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

public class MainTest {

    static final int EXIT_SUCCESS = 0
    static final int EXIT_FAILURE = 1
    ByteArrayOutputStream stdout
    ByteArrayOutputStream errout

    @BeforeMethod
    public void setUp() {
        stdout = new ByteArrayOutputStream()
        errout = new ByteArrayOutputStream()
    }

    @DataProvider(name = 'differentWaysToOutputUsageInfo')
    private Object[][] differentWaysToOutputHelp() {
        [
                [[] as String[], EXIT_FAILURE],
                [['-h'] as String[], EXIT_FAILURE],
                [['--help'] as String[], EXIT_FAILURE],
                [['help'] as String[], EXIT_SUCCESS],
        ] as Object[][]
    }

    @Test(dataProvider = 'differentWaysToOutputUsageInfo')
    public void outputsUsageInfo(args, expectedExitCode) {
        final main = getTestableMain()
        final exitCode = main.run('test', args)

        assertThat(stdout.toString(), isEmptyString())
        assertThat(errout.toString(), containsString('--help, -h'))
        assertThat(errout.toString(), containsString('help      Show help information about a command'))
        assertThat(exitCode, equalTo(expectedExitCode))
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
        final exitCode = main.run('test', args)

        assertThat(errout.toString(), isEmptyString())
        assertThat(stdout.toString(), equalTo('Thnx, received your positive feedback: did good\n'))
        assertThat(exitCode, equalTo(EXIT_SUCCESS))
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
        final exitCode = main.run('test', args)

        assertThat(errout.toString(), isEmptyString())
        assertThat(stdout.toString(), equalTo('Thnx, received your negative feedback: did bad\n'))
        assertThat(exitCode, equalTo(EXIT_SUCCESS))
    }

    @DataProvider(name = 'differentWaysToRunAScript')
    public Object[][] differentWaysToRunAScript() {
        [
                [['run', 'src/test/resources/helloworld.txt'] as String[]],
        ] as Object[][]
    }

    @Test(dataProvider = 'differentWaysToRunAScript')
    public void canRunAScript(args) {
        final main = getTestableMain()
        final exitCode = main.run('test', args)

        assertThat(errout.toString(), isEmptyString())
        assertThat(stdout.toString(), equalTo('Hello World'))
        assertThat(exitCode, equalTo(EXIT_SUCCESS))
    }

    @DataProvider(name = 'differentWaysToFailsWhenAScriptDoesNotExist')
    public Object[][] differentWaysToFailsWhenAScriptDoesNotExist() {
        [
                [['run', 'notexisting/notexisting.txt'] as String[]],
        ] as Object[][]
    }

    @Test(
            dataProvider = 'differentWaysToFailsWhenAScriptDoesNotExist',
            expectedExceptions = IllegalArgumentException
    )
    public void failsWhenAScriptDoesNotExist(args) {
        final main = getTestableMain()
        main.run('test', args)
    }

    private Main getTestableMain() {
        def main = new Main()
        main.outputConsumer = new PrintStream(stdout)
        main.errorConsumer = new PrintStream(errout)
        return main
    }
}
