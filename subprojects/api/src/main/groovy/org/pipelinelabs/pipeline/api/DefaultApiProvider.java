package org.pipelinelabs.pipeline.api;

import org.pipelinelabs.pipeline.util.ServiceLocator;

public class DefaultApiProvider implements ApiProvider {
    @Override
    public <T> T get(Class<T> clazz) {
        return new ServiceLocator().find(clazz);
    }
}
