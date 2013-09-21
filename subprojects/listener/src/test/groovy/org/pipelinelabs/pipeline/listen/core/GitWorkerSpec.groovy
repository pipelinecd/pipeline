package org.pipelinelabs.pipeline.listen.core

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import spock.lang.Ignore
import spock.lang.Specification

class GitWorkerSpec extends Specification {
    private EventBus bus = Mock(EventBus)

    def 'Start registers itself to the event bus'() {
        given:
        def worker = new GitWorker(bus)

        when:
        worker.start()

        then:
        1 * bus.register(worker)
    }

    def 'Stop unregisters itself from the event bus'() {
        given:
        def worker = new GitWorker(bus)

        when:
        worker.stop()

        then:
        1 * bus.unregister(worker)
    }

    def 'Work handles GitTriggerEvent and is annotated with @Subscribe'() {
        def method = GitWorker.getMethod("work", GitTriggerEvent)

        expect:
        method.isAnnotationPresent(Subscribe)
    }

    @Ignore('Needs to be testable somehow')
    def 'Work clones the git repo and starts pipe-runner'() {
        def worker = new GitWorker(bus)
        final event = Mock(GitTriggerEvent)

        expect:
        worker.work(event)
    }
}
