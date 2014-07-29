package cd.pipeline.api;

public interface ApiProvider {
    <T> T get(Class<T> clazz);
}
