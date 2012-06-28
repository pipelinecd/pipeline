package colony;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Mutable
public class JobResult {

    private JobConfig job;
    private boolean failed = false;

    private Map<String, Object> properties = new HashMap<String, Object>(0);

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

    public <T extends Collection<E>, E> T get(final String propertyName, final Class<T> type, final Class<E> subtype) {
        if (!properties.containsKey(propertyName)) {
            throw new IllegalArgumentException(String.format("Property '%s' to set", propertyName));
        }
        final Object value = properties.get(propertyName);
        if (value == null) {
            throw new IllegalArgumentException(String.format("Property '%s' has a null value", propertyName));
        }
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException(
                    String.format("Property '%s' has a non-expected value of type '%s'", propertyName,
                            value.getClass().getSimpleName()));
        }
        return (T) value;
    }

    public void put(final String propertyName, final Object value) {
        properties.put(propertyName, value);
    }
}
