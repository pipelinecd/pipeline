package org.pipelinelabs.pipeline.listen.core.git


class GitUtils {

    static String toBranchName(String gitRef) {
        return gitRef.split('/', 3).last()
    }
}
