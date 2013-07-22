package nl.ikoodi.cdtool.dsl.internal

import nl.ikoodi.cdtool.api.Task
import nl.ikoodi.cdtool.dsl.TaskDsl

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
