package wallboard.tasks;

import colony.JobResult;
import colony.Task;
import wallboard.tasks.hudson.HudsonJob;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class FetchHudsonJobsList implements Task {

    @Override
    public void execute(final JobResult job) {
        final List<HudsonJob> jobsList = new ArrayList<HudsonJob>();
        jobsList.add(new HudsonJob("job1", URI.create("http://localhost/job1")));
        jobsList.add(new HudsonJob("job2", URI.create("http://localhost/job2")));

        job.put("hudson.jobs", jobsList);
    }
}
