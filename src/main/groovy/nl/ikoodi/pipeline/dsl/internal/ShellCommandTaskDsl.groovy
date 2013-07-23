package nl.ikoodi.pipeline.dsl.internal

import nl.ikoodi.pipeline.api.Task
import nl.ikoodi.pipeline.dsl.TaskDsl

class ShellCommandTaskDsl implements TaskDsl, DslExporter<Task> {
    private String command;

    ShellCommandTaskDsl(String command) {
        this.command = command
    }

    @Override
    Task export() {
        return null
    }
}
