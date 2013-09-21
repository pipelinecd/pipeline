package stepdefs

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import org.pipelinelabs.pipeline.runner.cli.Main

this.metaClass.mixin Hooks
this.metaClass.mixin EN

def Main cli
def output

Given(~'^the command line application$') {->
    cli = new Main()
}

When(~'^I provide (.*) as parameter$') { param ->
    if (param == '') {
        param = [] as String
    }
    def orgOut = System.out
    System.out = output
    final exitCode = cli.run('pipe-runner', param)
    System.out = orgOut
    (exitCode == 0)
}

Then(~'^I expect to see the application help$') {->
    def help = '''
Usage: pipe-runner [options] [command] [command options]
  Options:
    --help, -h

       Default: false
  Commands:
    help      Show help information about a command
      Usage: help [options]

    run      Run, baby run!
      Usage: run [options]

    feedback      Give feedback about your experience with this tool
      Usage: feedback [options]
        Options:
          -n, --negative
             Your negative feedback message
          -p, --positive
             Your positive feedback message
    '''

    (help == output)
}
