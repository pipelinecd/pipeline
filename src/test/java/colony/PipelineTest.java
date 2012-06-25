package colony;

import colony.tasks.FailingTask;
import org.junit.Test;

import static org.junit.Assert.*;

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
}
