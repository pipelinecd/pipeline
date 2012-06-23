import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PipelineTest {

    @Test
    public void canHandleJobConfig() {
        JobConfig config = new JobConfig("My Job Name");

        Pipeline pipeline = new Pipeline();
        JobResult result = pipeline.handle(config);

        assertNotNull(result);
        assertEquals(config, result.getJob());
    }
}
