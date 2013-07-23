package nl.ikoodi.pipeline.cli

def main = new Main()
final exitStatus = main.run('beam', args)
System.exit(exitStatus)
