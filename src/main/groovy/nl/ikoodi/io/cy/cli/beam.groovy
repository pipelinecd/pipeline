package nl.ikoodi.io.cy.cli

def main = new Main()
final exitStatus = main.run('nl.ikoodi.io.cy.beam', args)
System.exit(exitStatus)
