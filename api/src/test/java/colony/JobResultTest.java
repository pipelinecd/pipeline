package colony;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

public class JobResultTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void requiresAJobConfig() {
        new JobResult(null);
    }

    @Test
    public void canGetJobConfig() {
        final JobConfig actualJob = getJobConfig();
        final JobResult result = new JobResult(actualJob);

        assertSame(result.getJob(), actualJob);
    }

    @Test
    public void canSetToFailed() {
        final JobConfig actualJob = getJobConfig();
        final JobResult result = new JobResult(actualJob);

        result.setFailed();

        assertTrue(result.isFailed());
    }

    @Test
    public void canPutAndGetListProperties() {
        final JobResult result = new JobResult(getJobConfig());
        final ArrayList<String> expectedProperties = new ArrayList<String>();
        final String propertyName = "my.task.results";
        result.put(propertyName, expectedProperties);

        final List actualProperties = result.get(propertyName, List.class, String.class);
        assertEquals(actualProperties, expectedProperties);
    }

    @Test(
            expectedExceptions = IllegalStateException.class
            , expectedExceptionsMessageRegExp = "Property 'my.task.results' has a value of type 'ArrayList' while expected to be of type 'Set'"
    )
    public void propertyValueTypeIsVerifiedOnGet() {
        final JobResult result = new JobResult(getJobConfig());
        final ArrayList<String> expectedProperties = new ArrayList<String>();
        final String propertyName = "my.task.results";
        result.put(propertyName, expectedProperties);

        result.get(propertyName, Set.class, String.class);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Property 'my.task.resultsinvalid' does not exist"
    )
    public void propertyNameMustExistOnGet() {
        final JobResult result = new JobResult(getJobConfig());
        final ArrayList<String> expectedProperties = new ArrayList<String>();
        final String propertyName = "my.task.results";
        result.put(propertyName, expectedProperties);

        final String invalidPropertyName = propertyName + "invalid";
        result.get(invalidPropertyName, List.class, String.class);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Property 'my.task.results' has a null value at position 1"
    )
    public void propertyCollectionValueMayNotContainNullElementsOnPut() {
        final JobResult result = new JobResult(getJobConfig());
        final ArrayList<String> expectedProperties = new ArrayList<String>();
        expectedProperties.add("testString");
        expectedProperties.add(null);
        final String propertyName = "my.task.results";
        result.put(propertyName, expectedProperties);
    }

    @Test(
            expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "Property value of 'my.task.results' may not be null"
    )
    public void propertyCollectionMayNotBeNull() {
        final JobResult result = new JobResult(getJobConfig());
        final ArrayList<String> expectedProperties = null;
        final String propertyName = "my.task.results";
        result.put(propertyName, expectedProperties);
    }

    @Test(
            expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "Property value of 'my.task.results' may not be null"
    )
    public void propertyObjectMayNotBeNull() {
        final JobResult result = new JobResult(getJobConfig());
        final Object expectedProperty = null;
        final String propertyName = "my.task.results";
        result.put(propertyName, expectedProperty);
    }

    private JobConfig getJobConfig() {
        final String jobName = "myjob";
        return new JobConfig(jobName);
    }
}
