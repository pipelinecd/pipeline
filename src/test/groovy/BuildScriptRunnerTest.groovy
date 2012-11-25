import nl.ikoodi.builder.BuildScriptRunner
import org.testng.annotations.Test

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
        runScript(script)
    }

    @Test
    public void echo() {
        String script = """
            echo "Hello World"
        """
        runScript(script)
    }

    private void runScript(final String script) {
        final BuildScriptRunner buildScript = new BuildScriptRunner(script);
        buildScript.run();
    }
}