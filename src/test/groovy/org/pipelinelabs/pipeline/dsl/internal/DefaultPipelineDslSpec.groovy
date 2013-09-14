package org.pipelinelabs.pipeline.dsl.internal

import spock.lang.Specification

class DefaultPipelineDslSpec extends Specification {
    def pipeline = new DefaultPipelineDsl();

    def "A stage has a name"() {
        when:
        def expectedStageName = 'commit'
        def stage = pipeline.stage(expectedStageName, {

        })

        then:
        assert stage.name == expectedStageName
    }

    def "The name of a stage cannot be changed from the stage closure"() {
        when:
        pipeline.stage('stageName', {
            name = 'changedStageName'
        })

        then:
        def e = thrown(MissingPropertyException)
        assert e.message.matches(/.*Cannot set readonly property: name for class:.*/)
    }

    def "A stage has an optional description which can be set from the stage closure"() {
        when:
        def expectedDescription = 'Commit stage description'
        def stage = pipeline.stage('commit', {
            description = expectedDescription
        })

        then:
        assert stage.description == expectedDescription
    }

    def "Cannot call pipeline methods from the stage closure"() {
        when:
        pipeline.stage('commit', {
            stage 'stageFromAStage', {

            }
        })

        then:
        thrown(MissingMethodException)
    }
}
