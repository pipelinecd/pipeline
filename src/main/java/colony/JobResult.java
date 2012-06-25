package colony;

// Mutable
public class JobResult {

    private JobConfig job;
    private boolean failed = false;

    public JobResult(final JobConfig job) {
        this.job = job;
    }

    public JobConfig getJob() {
        return job;
    }

    public void setFailed() {
        this.failed = true;
    }

    public boolean isFailed() {
        return failed;
    }
}
