package org.pipelinelabs.pipeline.runner.internal;

import org.pipelinelabs.pipeline.dsl.internal.DefaultPipelineDsl;
import org.pipelinelabs.pipeline.util.ServiceLookupException;

public class DefaultServiceRegistry implements ServiceRegistry {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final Class<T> serviceType) throws ServiceLookupException {
        final T service;
        switch (serviceType.getSimpleName()) {
            case "PipelineDsl":
                service = (T) new DefaultPipelineDsl();
                break;
            default:
                throw new UnsupportedServiceType(serviceType);
        }
        return service;
    }

    private class UnsupportedServiceType extends ServiceLookupException {

        private Class clazz;

        <T> UnsupportedServiceType(final Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public String getMessage() {
            final String msg = "Service type '%s' is not supported";
            return String.format(msg, clazz.getSimpleName());
        }
    }
}
