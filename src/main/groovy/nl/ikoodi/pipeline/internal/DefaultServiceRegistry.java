package nl.ikoodi.pipeline.internal;

public class DefaultServiceRegistry implements ServiceRegistry {

    @Override
    public <T> T get(Class<T> serviceType) throws ServiceLookupException {
        return null;
    }
}
