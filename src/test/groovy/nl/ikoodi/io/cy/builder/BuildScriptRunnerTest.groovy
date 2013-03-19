package nl.ikoodi.io.cy.builder

import org.testng.annotations.Test

import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.containsString
import static org.junit.Assert.assertThat

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
        def output = new StringWriter()
        def writer = new PrintWriter(output)
        final BuildScriptRunner buildScript = new BuildScriptRunner(writer, script)
        buildScript.run()
        return output.toString()
    }
}
