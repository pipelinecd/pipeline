package cd.pipeline.api;

import cd.pipeline.util.ServiceLocator;

public class DefaultApiProvider implements ApiProvider {
    @Override
    public <T> T get(Class<T> clazz) {
        return new ServiceLocator().find(clazz);
    }
}
