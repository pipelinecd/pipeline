package org.pipelinelabs.pipeline.listen

import spock.lang.Specification


class MainSpec extends Specification {

    def "Can start app without arguments"() {
        when:
        Main.main()

        then:
        noExceptionThrown()
    }
}
