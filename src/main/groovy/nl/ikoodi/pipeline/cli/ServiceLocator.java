package nl.ikoodi.pipeline.cli;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLocator {

    public <T> T get(Class<T> clazz) throws ServiceLookupException {
        final ServiceLoader<T> loader = ServiceLoader.load(clazz);
        final Iterator<T> iterator = loader.iterator();
        if (!iterator.hasNext()) {
            throw new ServiceLookupException();
        }
        return iterator.next();
    }
}
