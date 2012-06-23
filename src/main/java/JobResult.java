// Mutable
public class JobResult {

    private JobConfig job;

    public JobResult(final JobConfig job) {
        this.job = job;
    }

    public JobConfig getJob() {
        return job;
    }
}
