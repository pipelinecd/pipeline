package wallboard.tasks;

import colony.JobConfig;
import colony.JobResult;
import colony.Task;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import wallboard.tasks.hudson.FetchHudsonJobsList;
import wallboard.tasks.hudson.HudsonJob;
import wallboard.tasks.hudson.HudsonJobList;

import java.net.URI;

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
        final JobResult expectedResult = new JobResult(new JobConfig("basic"));
        final String propKey = "hudson.jobs";
        final HudsonJobList expectedJobsList = new HudsonJobList();
        expectedJobsList.add(new HudsonJob("job1", URI.create("http://localhost/job1")));
        expectedJobsList.add(new HudsonJob("job2", URI.create("http://localhost/job2")));
        expectedResult.put(propKey, expectedJobsList);

        final JobResult actualResult = new JobResult(new JobConfig("basic"));

        task.execute(actualResult);

        final HudsonJobList actualJobList = actualResult.get(propKey, HudsonJobList.class);
//        hudsonJobList.
//        final List<HudsonJob> actualJobList = actualResult.get(propKey, List.class, HudsonJob.class);

        assertFalse(actualResult.isFailed(), "Job should not be failed if successful enriched");
        assertEquals(actualJobList, expectedJobsList, "Job list is not as expected");
    }

    @Test
    public void fetchJobs() {
        final FetchHudsonJobsList jobsTask = (FetchHudsonJobsList) task;
        jobsTask.fetchHudsonJobs();
    }
}
