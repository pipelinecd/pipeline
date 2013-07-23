package nl.ikoodi.pipeline.cli

def main = new Main()
final exitStatus = main.run('pipe', args)
System.exit(exitStatus)
