package colony.stages;

import colony.JobResult;
import colony.Stage;
import colony.Task;

import java.util.LinkedList;
import java.util.List;

public class SerialStage implements Stage {

    private List<Task> tasks = new LinkedList<Task>();

    @Override
    public void add(final Task task) {
        tasks.add(task);
    }

    @Override
    public void execute(final JobResult job) {
        for (Task task : tasks) {
            task.execute(job);
        }
    }
}
