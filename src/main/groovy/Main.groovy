
class Main {
    def run(scriptName, args) {
        CliBuilder cli = new CliBuilder(usage: "${scriptName} <command>")
        cli.with {
            h longOpt: 'help', 'Show usage information'
            i longOpt: 'init', 'Init workspace'
        }
        OptionAccessor options = cli.parse(args)
        if (!options || options.h) {
            cli.usage()
            return
        }
        if (options.i) {
            init()
            return
        }
        println 'Invalid option'
        cli.usage()
    }

    private def init() {
        def proc = "git init".execute()
        proc.waitFor() ? print(proc.err.text) : print(proc.text)
    }
}
