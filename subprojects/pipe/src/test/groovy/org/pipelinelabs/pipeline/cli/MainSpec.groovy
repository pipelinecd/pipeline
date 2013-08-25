package org.pipelinelabs.pipeline.cli

import spock.lang.Specification
import spock.lang.Unroll

class MainSpec extends Specification {

    static final int EXIT_SUCCESS = 0
    static final int EXIT_FAILURE = 1
    def programName = 'test'
    def stdout = new ByteArrayOutputStream()
    def errout = new ByteArrayOutputStream()
    def main = getTestableMain()

    @Unroll
    def "Can execute main with argument(s) #args to get help info with an exitStatus of '#expectedExitStatus'"() {
        def actualExistStatus = main.run(programName, args as String[])

        expect:
        assert stdout.toString().isEmpty()
        assert errout.toString().contains('--help, -h')
        assert errout.toString().contains('help      Show help information about a command')
        assert actualExistStatus == expectedExitStatus

        where:
        args       || expectedExitStatus
        ['']       || EXIT_FAILURE
        ['-h']     || EXIT_FAILURE
        ['--help'] || EXIT_FAILURE
        ['help']   || EXIT_SUCCESS
    }

    @Unroll
    def "Can give positive feedback with command '#cmd' and option '#option'"() {
        def actualExistStatus = main.run(programName, cmd, option, 'did good')

        expect:
        assert errout.toString().isEmpty()
        assert stdout.toString().equals('Thnx, received your positive feedback: did good\n')
        assert actualExistStatus == EXIT_SUCCESS

        where:
        cmd        | option
        'feedback' | '-p'
        'feedback' | '--positive'
    }

    @Unroll
    def "Can give negative feedback with command '#cmd' and option '#option'"() {
        def actualExistStatus = main.run(programName, cmd, option, 'did bad')

        expect:
        assert errout.toString().isEmpty()
        assert stdout.toString().equals('Thnx, received your negative feedback: did bad\n')
        assert actualExistStatus == EXIT_SUCCESS

        where:
        cmd        | option
        'feedback' | '-n'
        'feedback' | '--negative'
    }

    def "Can run Pipeline from DSL script file"() {
        def actualExistStatus = main.run(programName, 'run', 'src/test/resources/helloworld.txt')

        expect:
        assert errout.toString().isEmpty()
        assert stdout.toString().contains('Hello World')
        assert actualExistStatus == EXIT_SUCCESS
    }

    def "Throws an IllegalArgumentException if DSL script file does not exist"() {
        when:
        main.run(programName, 'run', 'notexisting/notexisting.txt')

        then:
        thrown(IllegalArgumentException)
    }

    private Main getTestableMain() {
        def main = new Main()
        main.outputConsumer = new PrintStream(stdout)
        main.errorConsumer = new PrintStream(errout)
        return main
    }
}
