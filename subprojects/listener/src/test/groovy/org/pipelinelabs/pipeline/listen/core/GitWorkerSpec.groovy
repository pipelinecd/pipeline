package org.pipelinelabs.pipeline.listen.core

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import spock.lang.Ignore
import spock.lang.Specification

class GitWorkerSpec extends Specification {
    private EventBus bus = Mock(EventBus)
    private GitWorker worker = new GitWorker(bus)

    def 'Start registers itself to the event bus'() {
        when:
        worker.start()

        then:
        1 * bus.register(worker)
    }

    def 'Stop unregisters itself from the event bus'() {
        when:
        worker.stop()

        then:
        1 * bus.unregister(worker)
    }

    def 'work() is the event handler of GitTriggerEvent objects'() {
        def method = GitWorker.getMethod("work", GitTriggerEvent)

        expect:
        method.isAnnotationPresent(Subscribe)
    }

    @Ignore
    def 'Work clones the git repo and starts pipe-runner'() {
        final event = Mock(GitTriggerEvent)

        expect:
        worker.work(event)
    }
}
