package org.pipelinelabs.pipeline.listen

import spock.lang.Ignore
import spock.lang.Specification

class MainSpec extends Specification {

    @Ignore('Need to figure out how to mock the Main constructor or somehow better implemented Main')
    def 'Main should start the PipeListenService'() {
        def service = Mock(PipeListenService)
        def main = GroovySpy(Main, global: true)

        when:
        Main.main()

        then:
        1 * new Main()
        1 * main.createService() >> service
        1 * service.run()
    }
}
