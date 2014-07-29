package cd.pipeline.listen.core

import spock.lang.Specification

class GitTriggerEventSpec extends Specification {

    def 'Event requires an url and branch'() {
        def url = "url"
        def branch = "branch"

        expect:
        def event = new GitTriggerEvent(url, branch)
        event.url == url
        event.branch == branch
    }
}
