package org.pipelinelabs.pipeline.dsl.internal

import org.pipelinelabs.pipeline.api.task.ShellCommand

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
