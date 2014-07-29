package cd.pipeline.graphs

import spock.lang.Specification

class GraphSpec extends Specification {
    /**
     * - two stages executed in order of adding
     * - three stages where 1,2 are executed at the same time, 3 is executed after 1
     * - four stages where 1,2 are executed at the same time, 3 is executed after 1, 4 is executed after 2
     * - four stages where 2 executes after 1, 3 and 4 are executed after 2
     */

    def "Can execute stages after eachother"() {
        given: "multiple stages are added as dependend on eachother"

        when: "the pipeline is executed"

        then: "each next stage is executed when the previous one finishes successfully"
    }

    def "Can execute stages in parallel"() {
    }

    def "Multiple stages can join together to one next stage"() {
        given: "multiple stages join together to one next stage"

        when: "the next stage is reached"

        then: "the stage is not executed until all joining stages are ready"
    }

    def "Can execute stages in parallel and join"() {
    }
}
