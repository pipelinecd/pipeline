package colony;

import java.util.HashMap;
import java.util.Map;

// @Immutable
public class JobConfig {

    private String name;

    private Map<String, Object> properties = new HashMap<String, Object>(0);

    public JobConfig(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
