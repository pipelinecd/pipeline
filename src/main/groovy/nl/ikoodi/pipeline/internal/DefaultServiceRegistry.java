package nl.ikoodi.pipeline.internal;

import nl.ikoodi.pipeline.cli.ServiceLookupException;
import nl.ikoodi.pipeline.runner.DefaultPipeline;

public class DefaultServiceRegistry implements ServiceRegistry {

    @Override
    public <T> T get(Class<T> serviceType) throws ServiceLookupException {
        final T service;
        switch (serviceType.getCanonicalName()) {
            case "Pipeline":
                service = (T) new DefaultPipeline();
                break;
            default:
                throw new RuntimeException();
        }
        return service;
    }
}
