package org.pipelinelabs.pipeline.dsl.internal

import org.pipelinelabs.pipeline.dsl.EmailMessengerDsl
import org.pipelinelabs.pipeline.messenger.internal.EmailMessenger
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

class DefaultEmailMessengerDsl implements EmailMessengerDsl {
    String host
    int port
    String username
    String password
    String from

    EmailMessenger toMessenger() {
        JavaMailSender sender = new JavaMailSenderImpl().with {
            host = this.host
            port = this.port
            username = this.username
            password = this.password
            it
        }
        return new EmailMessenger(sender, from)
    }
}
