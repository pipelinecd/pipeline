package colony.tasks;

import colony.JobResult;
import colony.Task;

public class FailingTask implements Task {

    @Override
    public void execute(final JobResult job) {
        job.setFailed();
    }
}
