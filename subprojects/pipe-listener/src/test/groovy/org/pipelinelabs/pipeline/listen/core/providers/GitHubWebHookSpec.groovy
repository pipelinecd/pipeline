package org.pipelinelabs.pipeline.listen.core.providers

import spock.lang.Specification


class GitHubWebHookSpec extends Specification {
    private GitHubWebHook provider = new GitHubWebHook()

    def 'Is the GitHub provider'() {
        expect:
        provider.name == 'GitHub'
    }

    def 'Listens to the \'github\' uri'() {
        expect:
        provider.nameForUri == 'github'
    }

    def 'Supports GitHubNotification'() {
        expect:
        provider.supports() == GitHubNotification
    }
}
