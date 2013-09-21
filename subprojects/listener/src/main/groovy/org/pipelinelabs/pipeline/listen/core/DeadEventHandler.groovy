package org.pipelinelabs.pipeline.listen.core

import com.google.common.eventbus.DeadEvent
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.yammer.dropwizard.lifecycle.Managed
import groovy.util.logging.Slf4j

@Slf4j
class DeadEventHandler implements Managed {

    private final EventBus bus

    DeadEventHandler(EventBus bus) {
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
    void work(DeadEvent event) {
        log.error("No event handler available for event [{}]", event.event)
    }
}
