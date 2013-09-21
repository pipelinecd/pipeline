package org.pipelinelabs.pipeline.pipe.cli.dsl

import org.pipelinelabs.pipeline.pipe.internal.DefaultServiceRegistry
import spock.lang.Ignore
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.Matchers.containsString
import static spock.util.matcher.HamcrestSupport.that

class PipelineScriptRunnerSpec extends Specification {

    @Ignore
    def "Cannot call pipeline methods from the stage closure"() {
        def script = """
            stage 'commit', {
                stage 'stageFromAStage', {

                }
            }
        """
        when:
        runScript(script)

        then:
        thrown(MissingMethodException)
    }

    def "Can use build-in groovy println"() {
        def script = """
            println 'Hello World'
        """

        expect:
        def output = runScript(script)
        that output, is(equalTo('Hello World\n'))
    }

    def "Can execute stage with build-in groovy println"() {
        def script = """
            stage 'hello', {
                println 'Hello Using my own binding'
            }
        """

        expect:
        def output = runScript(script)
        that output, is(equalTo('Hello Using my own binding\n'))
    }

    def "Can execute self-defined echo"() {
        def script = """
            echo 'Hello %s', 'World'
        """

        expect:
        def output = runScript(script)
        that output, is(equalTo('Hello World'))
    }

    def "Can execute stage with self-defined echo"() {
        def script = """
            stage 'hello', {
                echo 'Hello %s', 'World'
            }
        """

        expect:
        def output = runScript(script)
        that output, is(equalTo('Hello World'))
    }

    def "Can execute system command from stage"() {
        def script = """
            stage 'execute dir system command', {
                run "dir"
            }
        """

        expect:
        def output = runScript(script)
        that output, containsString('runner.gradle')
    }


    private String runScript(final String script) {
        def output = new ByteArrayOutputStream()
        def stream = new PrintStream(output)
        def registry = new DefaultServiceRegistry()
        final PipelineScriptRunner buildScript = new PipelineScriptRunner(registry, stream, script)

        buildScript.run()

        return output.toString()
    }
}
