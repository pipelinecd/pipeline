package wallboard.tasks;

import colony.JobConfig;
import colony.JobResult;
import colony.Task;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class FetchHudsonJobsListTest {

    private Task task;

    @BeforeTest
    public void setUp() {
        task = new FetchHudsonJobsList();
    }

    @Test
    public void enrichesJobResultWithHudsonList() {
        final JobResult job = new JobResult(new JobConfig("basic"));
        task.execute(job);

        assertFalse(job.isFailed());
    }
}
