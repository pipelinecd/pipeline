package nl.ikoodi.io.cy.cli

def main = new Main()
final exitStatus = main.run('beam', args)
System.exit(exitStatus)
