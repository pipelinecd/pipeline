#language: en
@cli
Feature: Command line help
  As an user
  I want to be able to get help information on the command line
  so I know what options are available

  Scenario Outline: Provide help
    Given the command line application
    When I provide <param> as parameter
    Then I expect to see the application help

  Examples: Using <param>
    | param  |
    | --help |
    | -h     |
    |        |
