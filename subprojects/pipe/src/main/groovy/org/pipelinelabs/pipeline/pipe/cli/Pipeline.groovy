package org.pipelinelabs.pipeline.pipe.cli

def main = new Main()
final exitStatus = main.run('pipe', args)
System.exit(exitStatus)
