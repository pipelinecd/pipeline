package org.pipelinelabs.pipeline.pipe.dsl.internal

import org.pipelinelabs.pipeline.pipe.api.task.ShellCommand

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
