package org.pipelinelabs.pipeline.runner.messenger.internal

import org.pipelinelabs.pipeline.runner.event.PipeEvent
import org.pipelinelabs.pipeline.runner.messenger.MessageContext
import org.pipelinelabs.pipeline.runner.messenger.SpecializedMessenger
import spock.lang.Specification

class AggregateMessengerSpec extends Specification {
    MessageContext context = Mock()
    PipeEvent event = new PipeEvent('', '', '', '')

    def 'if no messengers, does nothing'() {
        given:
        def aggregate = new AggregateMessenger([])
        when:
        aggregate.process(context, event)
        then:
        notThrown(Exception)
    }

    def 'if no messengers accept context, does not process'() {
        given:
        def messenger1 = Mock(SpecializedMessenger)
        def messenger2 = Mock(SpecializedMessenger)
        def aggregate = new AggregateMessenger([messenger1, messenger2])
        when:
        aggregate.process(context, event)
        then:
        1 * messenger1.accepts(_) >> false
        1 * messenger2.accepts(_) >> false
        0 * messenger1.process(_, _)
        0 * messenger2.process(_, _)
    }

    def 'only messengers that accept context process the event'() {
        given:
        def messenger1 = Mock(SpecializedMessenger)
        def messenger2 = Mock(SpecializedMessenger)
        def messenger3 = Mock(SpecializedMessenger)
        def messenger4 = Mock(SpecializedMessenger)
        def aggregate = new AggregateMessenger([messenger1, messenger2,
                messenger3, messenger4])
        when:
        aggregate.process(context, event)
        then:
        1 * messenger1.accepts(context) >> false
        1 * messenger2.accepts(context) >> true
        1 * messenger3.accepts(context) >> true
        1 * messenger4.accepts(context) >> false
        0 * messenger1.process(_, _)
        1 * messenger2.process(context, event)
        1 * messenger3.process(context, event)
        0 * messenger4.process(_, _)
    }
}
