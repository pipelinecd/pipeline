package nl.ikoodi.pipeline.dsl.internal

import nl.ikoodi.pipeline.api.task.ShellCommand

class ShellCommandTaskDsl implements InternalTaskDsl {
    private String command;

    ShellCommandTaskDsl(String command) {
        this.command = command
    }

    @Override
    ShellCommand export() {
        return null
    }
}
