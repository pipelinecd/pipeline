package colony;

import colony.stages.SerialStage;
import colony.tasks.FailingTask;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PipelineTest {

    @Test
    public void canHandleJobConfig() {
        JobConfig config = new JobConfig("My Job Name");

        Pipeline pipeline = new Pipeline();
        JobResult result = pipeline.handle(config);

        assertNotNull(result);
        assertEquals(config, result.getJob());
    }

    @Test
    public void jobIsNotFailedWhenEverythingWentWell() {
        JobConfig config = new JobConfig("My Job Name");

        Pipeline pipeline = new Pipeline();
        JobResult result = pipeline.handle(config);

        assertNotNull(result);
        assertFalse(result.isFailed());
    }

    @Test
    public void jobIsFailedWhenAtleastOneThingFailed() {
        JobConfig config = new JobConfig("My Job Name");

        Pipeline pipeline = new Pipeline();
        pipeline.add(new FailingTask());

        JobResult result = pipeline.handle(config);

        assertNotNull(result);
        assertTrue(result.isFailed());
    }

    @Test
    public void canRunTasksInStages() {
        JobConfig config = new JobConfig("My Job Name");

        Pipeline pipeline = new Pipeline();
        final SerialStage stage = new SerialStage();
        stage.add(new FailingTask());
        pipeline.add(stage);

        JobResult result = pipeline.handle(config);

        assertNotNull(result);
        assertTrue(result.isFailed());
    }
}
