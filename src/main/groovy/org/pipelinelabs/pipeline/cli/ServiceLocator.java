package org.pipelinelabs.pipeline.cli;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

public class ServiceLocator {

    /**
     * Find an implementation for the given class type.
     *
     * @param clazz Class type to find an implementation for
     * @return Instance of the found implementation
     * @throws ServiceLookupException When no implementation is found,
     *                                or when multiple implementations are found
     */
    public <T> T find(Class<T> clazz) throws ServiceLookupException {
        final Set<T> found = findAll(clazz);
        if (found.isEmpty()) {
            throw new NoServiceImplementationFound(clazz);
        }
        if (found.size() != 1) {
            throw new MultipleServiceImplementationsFound(clazz);
        }
        return found.iterator().next();
    }

    public <T> Set<T> findAll(Class<T> clazz) {
        final ServiceLoader<T> loader = ServiceLoader.load(clazz);
        return newSet(loader.iterator());
    }

    private <T> Set<T> newSet(Iterator<T> it) {
        final HashSet<T> set = new HashSet<>();
        while (it.hasNext()) {
            set.add(it.next());
        }
        return set;
    }

    class NoServiceImplementationFound extends ServiceLookupException {

        private Class clazz;

        <T> NoServiceImplementationFound(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public String getMessage() {
            final String msg = "Found no implementation for '%s'";
            return String.format(msg, clazz.getSimpleName());
        }
    }

    class MultipleServiceImplementationsFound extends ServiceLookupException {

        private Class clazz;

        <T> MultipleServiceImplementationsFound(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public String getMessage() {
            final String msg = "Found multiple implementations for '%s', expected only one implementation";
            return String.format(msg, clazz.getSimpleName());
        }
    }
}
