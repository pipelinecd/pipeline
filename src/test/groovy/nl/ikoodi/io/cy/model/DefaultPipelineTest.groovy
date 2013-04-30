package nl.ikoodi.io.cy.model

import org.testng.annotations.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

class DefaultPipelineTest {

    @Test
    public void stageShouldHaveAName() {
        def pipeline = new DefaultPipeline();
        def expectedStageName = 'commit'
        def stage = pipeline.stage(expectedStageName, {

        })
        assertThat(stage.name, equalTo(expectedStageName))
    }

    @Test
    public void stageShouldCanHaveADescriptionSetViaClosure() {
        def pipeline = new DefaultPipeline();
        def expectedDescription = 'Commit stage description'
        def stage = pipeline.stage('commit', {
            description = expectedDescription
        })
        assertThat(stage.description, equalTo(expectedDescription))
    }

    @Test(
            expectedExceptions = MissingPropertyException
            , expectedExceptionsMessageRegExp = 'No such property: name for class.*'
    )
    public void stageShouldNotAllowTryingToChangeTheStageNameInTheClosure() {
        def pipeline = new DefaultPipeline();
        pipeline.stage('stageName', {
            name = 'changedStageName'
        })
    }
}
