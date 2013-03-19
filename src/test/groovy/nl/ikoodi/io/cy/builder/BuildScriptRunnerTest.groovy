package nl.ikoodi.io.cy.builder

import org.testng.annotations.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.containsString

class BuildScriptRunnerTest {

    @Test
    public void runHelloWorld() {
        String script = """
            println "Hello World";
        """
        runScript(script)
    }

    @Test
    public void useOwnBinding() {
        String script = """
            stage 'hello', {
                println "Hello Using my own binding"
            }
        """
        def output = runScript(script)
        assertThat(output, containsString('Hello Using my own binding'))
    }

    @Test
    public void echo() {
        String script = """
            echo "Hello World"
        """
        def output = runScript(script)
        println(output)
        assertThat(output, containsString('Hello World'))
    }

    @Test
    public void echoWithStringFormatting() {
        String script = """
            echo "Hello %s", "World"
        """
        def output = runScript(script)
        assertThat(output, containsString('Hello World'))
    }

    @Test
    public void executeSystemCommand() {
        String script = """
            // Change directory to current directory
            run "dir"
        """
        def output = runScript(script)
        println(output)
        assertThat(output, allOf(
                containsString('build.gradle')
                , containsString('gradlew')
        ))
    }


    private String runScript(final String script) {
        def output = new ByteArrayOutputStream()
        def stream = new PrintStream(output)
        final BuildScriptRunner buildScript = new BuildScriptRunner(stream, script)

        buildScript.run()

        return output.toString()
    }
}
