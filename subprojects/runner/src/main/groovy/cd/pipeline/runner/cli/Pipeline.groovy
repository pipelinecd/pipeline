package cd.pipeline.runner.cli

def main = new Main()
final exitStatus = main.run('pipe-runner', args)
System.exit(exitStatus)
