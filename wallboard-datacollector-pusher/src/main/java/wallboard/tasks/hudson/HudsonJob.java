package wallboard.tasks.hudson;

import java.net.URI;

public class HudsonJob {

    private String name;

    private URI uri;

    public HudsonJob(final String name, final URI uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final HudsonJob hudsonJob = (HudsonJob) o;

        if (name != null ? !name.equals(hudsonJob.name) : hudsonJob.name != null) return false;
        if (uri != null ? !uri.equals(hudsonJob.uri) : hudsonJob.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HudsonJob{" +
                "name='" + name + '\'' +
                ", uri=" + uri +
                '}';
    }
}
