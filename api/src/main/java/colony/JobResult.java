package colony;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

// Mutable
public class JobResult {

    private JobConfig job;
    private boolean failed = false;

    private Map<String, Object> properties = new HashMap<String, Object>(0);

    public JobResult(final JobConfig job) {
        notNull(job, "A JobConfig must be given");
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
            throw new IllegalArgumentException(String.format("Property '%s' does not exist", propertyName));
        }
        final Object value = properties.get(propertyName);
        if (!type.isInstance(value)) {
            throw new IllegalStateException(
                    String.format("Property '%s' has a value of type '%s' while expected to be of type '%s'",
                            propertyName,
                            value.getClass().getSimpleName(), type.getSimpleName()));
        }
        return (T) value;
    }

    public void put(final String propertyName, final Object value) {
        valueMayNotBeNull(propertyName, value);
        putForReal(propertyName, value);
    }

    public void put(final String propertyName, final Collection value) {
        valueMayNotBeNull(propertyName, value);
        noNullElements(value, "Property '%s' has a null value at position %d", propertyName);
        putForReal(propertyName, value);
    }

    private void putForReal(final String propertyName, final Object value) {
        properties.put(propertyName, value);
    }

    private void valueMayNotBeNull(final String propertyName, final Object value) {
        notNull(value, String.format("Property value of '%s' may not be null", propertyName));
    }
}
