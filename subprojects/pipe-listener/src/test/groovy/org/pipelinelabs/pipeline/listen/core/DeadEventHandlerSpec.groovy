package org.pipelinelabs.pipeline.listen.core

import com.google.common.eventbus.DeadEvent
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import spock.lang.Ignore
import spock.lang.Specification


class DeadEventHandlerSpec extends Specification {
    private EventBus bus = Mock(EventBus)

    def 'Start registers itself to the event bus'() {
        given:
        def worker = new DeadEventHandler(bus)

        when:
        worker.start()

        then:
        1 * bus.register(worker)
    }

    def 'Stop unregisters itself from the event bus'() {
        given:
        def worker = new DeadEventHandler(bus)

        when:
        worker.stop()

        then:
        1 * bus.unregister(worker)
    }

    def 'Work handles DeadEvent and is annotated with @Subscribe'() {
        def method = DeadEventHandler.getMethod("work", DeadEvent)

        expect:
        method.isAnnotationPresent(Subscribe)
    }

    @Ignore('How to verify if injected logger is used?')
    def 'Work logs an error'() {
        def worker = new DeadEventHandler(bus)
        final event = Mock(DeadEvent)

        expect:
        worker.work(event)
    }
}
