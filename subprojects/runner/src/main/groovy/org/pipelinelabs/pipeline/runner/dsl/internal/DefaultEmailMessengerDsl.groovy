package org.pipelinelabs.pipeline.runner.dsl.internal

import org.pipelinelabs.pipeline.runner.dsl.EmailMessengerDsl
import org.pipelinelabs.pipeline.runner.messenger.internal.EmailMessenger
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

class DefaultEmailMessengerDsl implements EmailMessengerDsl {
    String host
    int port
    boolean tls = false
    String username
    String password
    String from

    EmailMessenger toMessenger() {
        JavaMailSender sender = new JavaMailSenderImpl()
        sender.with {
            host = this.host
            port = this.port
            username = this.username
            password = this.password
            if (tls) {
                javaMailProperties = new Properties()
                javaMailProperties['mail.smtp.starttls.enable'] = 'true'
            }
        }
        return new EmailMessenger(sender, from)
    }
}
