package wallboard.tasks;

import colony.JobResult;
import colony.Task;
import wallboard.tasks.hudson.HudsonJob;
import wallboard.tasks.hudson.HudsonJobList;

public class FetchHudsonJobsList implements Task {



    @Override
    public void execute(final JobResult job) {


        final HudsonJobList jobList = new HudsonJobList();
//        jobList.with {
//            add()
//        }
        jobList.add(new HudsonJob("job1", URI.create("http://localhost/job1")));
        jobList.add(new HudsonJob("job2", URI.create("http://localhost/job2")));

        job.put("hudson.jobs", jobList);
    }
}
