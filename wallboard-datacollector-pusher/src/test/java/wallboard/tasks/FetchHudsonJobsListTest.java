package wallboard.tasks;

import colony.JobConfig;
import colony.JobResult;
import colony.Task;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import wallboard.tasks.hudson.HudsonJob;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class FetchHudsonJobsListTest {

    private Task task;

    @BeforeTest
    public void setUp() {
        task = new FetchHudsonJobsList();
    }

    @Test
    public void enrichesJobResultWithHudsonList() {
        final JobResult expectedJob = new JobResult(new JobConfig("basic"));
        final String propKey = "hudson.jobs";
        final ArrayList<HudsonJob> expectedJobsList = new ArrayList<HudsonJob>();
        expectedJobsList.add(new HudsonJob("job1", URI.create("http://localhost/job1")));
        expectedJobsList.add(new HudsonJob("job2", URI.create("http://localhost/job2")));
        expectedJob.put(propKey, expectedJobsList);

        final JobResult actualJob = new JobResult(new JobConfig("basic"));

        task.execute(actualJob);

        final List<HudsonJob> actualList = actualJob.get(propKey, List.class, HudsonJob.class);

        assertFalse(actualJob.isFailed());
        assertEquals(actualList, expectedJobsList);
    }
}
