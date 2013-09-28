package org.pipelinelabs.pipeline.runner.dsl;

import org.pipelinelabs.pipeline.runner.messenger.internal.EmailMessenger;

public interface EmailMessengerDsl {
    String getHost();

    void setHost(String host);

    int getPort();

    void setPort(int port);

    boolean getTls();

    void setTls(boolean tls);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getFrom();

    void setFrom(String from);

    EmailMessenger toMessenger();
}
