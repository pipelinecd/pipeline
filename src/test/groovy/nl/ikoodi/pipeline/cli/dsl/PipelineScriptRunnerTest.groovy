package nl.ikoodi.pipeline.cli.dsl

import org.testng.annotations.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

class PipelineScriptRunnerTest {

    @Test
    public void canUseWithBuildInGroovyPrintln() {
        String script = """
            println "Hello World";
        """
        def output = runScript(script)
        assertThat(output, is(equalTo('Hello World\n')));
    }

    @Test
    public void executeNamedStageWithBuildInGroovyPrintln() {
        String script = """
            stage 'hello', {
                println "Hello Using my own binding"
            }
        """
        def output = runScript(script)
        assertThat(output, is(equalTo('Hello Using my own binding\n')));
    }

    @Test
    public void executeNamedStageWithSelfDefinedEcho() {
        String script = """
            stage 'hello', {
                echo "Hello %s", "World"
            }
        """
        def output = runScript(script)

        assertThat(output, equalTo('Hello World'));
    }

    @Test
    public void canUseSelfDefinedEcho() {
        String script = """
            echo "Hello World"
        """
        def output = runScript(script)
        assertThat(output, equalTo('Hello World'));
    }

    @Test
    public void canUseSelfDefinedEchoWithStringFormatting() {
        String script = """
            echo "Hello %s", "World"
        """
        def output = runScript(script)
        assertThat(output, equalTo('Hello World'));
    }

    @Test
    public void canExecuteSystemCommandFromAStage() {
        String script = """
            stage 'execute dir system command', {
                run "dir"
            }
        """
        def output = runScript(script)
        assertThat(output, allOf(
                containsString('build.gradle')
                , containsString('gradlew')
        ))
    }

    private String runScript(final String script) {
        def output = new ByteArrayOutputStream()
        def stream = new PrintStream(output)
        final PipelineScriptRunner buildScript = new PipelineScriptRunner(stream, script)

        buildScript.run()

        return output.toString()
    }
}