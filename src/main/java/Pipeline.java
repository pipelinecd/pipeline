public class Pipeline {

    public JobResult handle(final JobConfig config) {
        System.out.println(String.format("Running job \"%s\"", config.getName()));
        final JobResult result = new JobResult(config);
        System.out.println(String.format("Finished job \"%s\"", config.getName()));
        return result;
    }
}
