package org.pipelinelabs.pipeline.cli.command;

public interface Command {

    /**
     * Get the name of the command.
     *
     * @return Name of the command, may never be null
     */
    String getName();

    /**
     * Can this command handle the given command name?
     *
     * @param commandName Command name asked to be handle
     * @return true if this command can handle the command name, false otherwise
     */
    boolean canHandle(String commandName);

    /**
     * Handle this command.
     */
    void handle();
}
