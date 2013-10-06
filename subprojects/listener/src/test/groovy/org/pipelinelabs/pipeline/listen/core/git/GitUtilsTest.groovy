package org.pipelinelabs.pipeline.listen.core.git

import spock.lang.Specification
import spock.lang.Unroll


class GitUtilsTest extends Specification {

    @Unroll
    def "Get branchname '#branch' from gitref '#ref'"() {
        expect:
        GitUtils.toBranchName(ref) == branch

        where:
        ref                     || branch
        'refs/heads/somebranch' || 'somebranch'
        '+refs/pull/42/merge'   || '42/merge'
    }
}
