package org.pipelinelabs.pipeline.runner.dsl.internal

import org.pipelinelabs.pipeline.runner.api.task.ShellCommand

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
