package org.pipelinelabs.pipeline.listen.core.git

import spock.lang.Specification
import spock.lang.Unroll


class GithubUtilsSpec extends Specification {

    @Unroll
    def 'Convert new URI("#uri") to github ssh url "#expected"'() {
        expect:
        final actual = GithubUtils.toGithubSshUrl(new URI(uri))
        actual == expected

        where:
        uri                                    || expected
        'https://github.com/octokitty/testing' || 'git@github.com:/octokitty/testing.git'
        'http://github.com/octokitty/testing'  || 'git@github.com:/octokitty/testing.git'
        'http://github.com//octokitty/testing' || 'git@github.com:/octokitty/testing.git'
        'http://github.com/octokitty//testing' || 'git@github.com:/octokitty/testing.git'
    }

    @Unroll
    def 'Convert "#uri" to github ssh url "#expected"'() {
        expect:
        final actual = GithubUtils.toGithubSshUrl(uri)
        actual == expected

        where:
        uri                                    || expected
        'https://github.com/octokitty/testing' || 'git@github.com:/octokitty/testing.git'
        'http://github.com/octokitty/testing'  || 'git@github.com:/octokitty/testing.git'
        'http://github.com//octokitty/testing' || 'git@github.com:/octokitty/testing.git'
        'http://github.com/octokitty//testing' || 'git@github.com:/octokitty/testing.git'
    }
}
