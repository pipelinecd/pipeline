package org.pipelinelabs.pipeline.pipe.cli;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * Locate services.
 * <p/>
 * Services are located via the standard Java 6+ way of discovering services,
 * as done by the {@link ServiceLoader}.
 *
 * @see ServiceLoader
 */
public class ServiceLocator {

    /**
     * Find an implementation for the given class type.
     *
     * @param clazz Class type to find an implementation for
     * @return Instance of the found implementation
     * @throws ServiceLookupException When no implementation is found,
     *                                or when multiple implementations are found
     */
    public <T> T find(final Class<T> clazz) throws ServiceLookupException {
        final Set<T> found = findAll(clazz);
        if (found.isEmpty()) {
            throw new NoServiceImplementationFound(clazz);
        }
        if (found.size() != 1) {
            throw new MultipleServiceImplementationsFound(clazz);
        }
        return found.iterator().next();
    }

    /**
     * Find all available implementations for the given class type.
     *
     * @param clazz Class type to find an implementation for
     * @return Set of instances of the found implementations
     */
    public <T> Set<T> findAll(final Class<T> clazz) {
        final ServiceLoader<T> loader = ServiceLoader.load(clazz);
        return newSet(loader.iterator());
    }

    private <T> Set<T> newSet(final Iterator<T> it) {
        final HashSet<T> set = new HashSet<>();
        while (it.hasNext()) {
            set.add(it.next());
        }
        return set;
    }

    private class NoServiceImplementationFound extends ServiceLookupException {

        private Class clazz;

        <T> NoServiceImplementationFound(final Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public String getMessage() {
            final String msg = "Found no implementation for '%s'";
            return String.format(msg, clazz.getSimpleName());
        }
    }

    private class MultipleServiceImplementationsFound extends ServiceLookupException {

        private Class clazz;

        <T> MultipleServiceImplementationsFound(final Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public String getMessage() {
            final String msg = "Found multiple implementations for '%s', expected only one implementation";
            return String.format(msg, clazz.getSimpleName());
        }
    }
}
