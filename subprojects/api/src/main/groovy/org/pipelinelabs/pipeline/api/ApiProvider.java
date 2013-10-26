package org.pipelinelabs.pipeline.api;

public interface ApiProvider {
    <T> T get(Class<T> clazz);
}
