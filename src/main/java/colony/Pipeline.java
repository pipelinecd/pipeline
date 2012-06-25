package colony;

import java.util.LinkedList;
import java.util.List;

public class Pipeline {

    private List<Task> tasks = new LinkedList<Task>();

    public JobResult handle(final JobConfig config) {
        System.out.println(String.format("Running job \"%s\"", config.getName()));
        final JobResult result = new JobResult(config);
        for (Task task : tasks) {
            task.execute(result);
        }

        System.out.println(String.format("Finished job \"%s\"", config.getName()));
        return result;
    }

    public void add(final Task task) {
        tasks.add(task);
    }
}
