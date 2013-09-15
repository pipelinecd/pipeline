package org.pipelinelabs.pipeline.listen.core

import spock.lang.Specification

class GitTriggerEventSpec extends Specification {

    def 'Event has an url'() {
        def url = "url"

        expect:
        new GitTriggerEvent(url).url == url
    }
}
