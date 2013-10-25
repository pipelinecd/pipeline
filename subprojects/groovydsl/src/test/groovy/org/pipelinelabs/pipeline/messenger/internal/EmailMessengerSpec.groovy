package org.pipelinelabs.pipeline.messenger.internal

import org.pipelinelabs.pipeline.event.PipeEvent
import org.pipelinelabs.pipeline.messenger.MessageContext
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

import javax.mail.Message.RecipientType
import javax.mail.Session
import javax.mail.internet.MimeMessage

class EmailMessengerSpec extends Specification {

    def 'accepts EmailContexts'() {
        expect:
        new EmailMessenger(null, null).accepts(new EmailContext([], []))
    }

    def 'does not accept other contexts'() {
        expect:
        !new EmailMessenger(null, null).accepts(Mock(MessageContext))
    }

    def 'process generates the correct email'() {
        given:
        PipeEvent event = new PipeEvent(
                'MyProject',
                'Success',
                'Gradle build succeeded in 10 seconds.',
                'a bunch of\nlog data\n')
        def from = '1@2.com'
        def to = ['a@b.com', 'c@d.com']
        def cc = ['e@f.com']
        EmailContext context = new EmailContext(to, cc)

        def email = new MimeMessage(null as Session)
        def sender = Mock(JavaMailSender)
        def messenger = new EmailMessenger(sender, from)

        when:
        messenger.process(context, event)

        then:
        1 * sender.createMimeMessage() >> { email }
        1 * sender.send({ MimeMessage msg ->
            msg.from.collect { it as String } == [from] &&
                    msg.getRecipients(RecipientType.TO).collect { it as String } == to &&
                    msg.getRecipients(RecipientType.CC).collect { it as String } == cc &&
                    msg.subject == '[pipeline] MyProject: Success' &&
                    msg.content == """\
                        Pipeline Name: MyProject
                        Status:        Success
                        Description:   Gradle build succeeded in 10 seconds.
                        Details:
                        a bunch of
                        log data
                        """.stripIndent()
        } as MimeMessage)
    }
}
