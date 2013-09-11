package org.pipelinelabs.pipeline.dsl;

import org.pipelinelabs.pipeline.messenger.internal.EmailMessenger;

public interface EmailMessengerDsl {
    String getHost();
    void setHost(String host);
    int getPort();
    void setPort(int port);
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    String getFrom();
    void setFrom(String from);
    EmailMessenger toMessenger();
}
