package wallboard;

import colony.Pipeline;import wallboard.tasks.FetchHudsonJobsList;

public class Main {

    public static void main(String... args) {

        final Pipeline pipeline = new Pipeline();
        pipeline.add(new FetchHudsonJobsList());
}
}