package org.pipelinelabs.pipeline.listen.core.providers

import org.pipelinelabs.pipeline.listen.api.Provider

class GitHubWebHook implements Provider<GitHubNotification> {

    @Override
    String getName() {
        'GitHub'
    }

    @Override
    String getNameForUri() {
        'github'
    }

    @Override
    Class<GitHubNotification> supports() {
        GitHubNotification
    }
}