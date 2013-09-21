package org.pipelinelabs.pipeline.listen.core

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.yammer.dropwizard.lifecycle.Managed

import java.nio.file.Files

class GitWorker implements Managed {

    private final EventBus bus

    GitWorker(EventBus bus) {
        this.bus = bus
    }

    @Override
    void start() throws Exception {
        bus.register(this)
    }

    @Override
    void stop() throws Exception {
        bus.unregister(this)
    }

    @Subscribe
    void work(GitTriggerEvent event) {
        def dir = Files.createTempDirectory("pipeline/")
        List envProps = null
        "git clone ${event.url}".execute(envProps, dir.toFile())
        "pipe-runner run project.pipe".execute(envProps, dir.toFile())
    }
}
