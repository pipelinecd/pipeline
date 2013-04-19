package nl.ikoodi.io.cy.model

import org.testng.annotations.Test

class PipelineTest {

    @Test
    public void stageShouldHaveANameAndClosureApplied() {
        def pipeline = new Pipeline();
        pipeline.stage('commit', {

        })
    }
}
