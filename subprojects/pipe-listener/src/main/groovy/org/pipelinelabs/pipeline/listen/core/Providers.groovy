package org.pipelinelabs.pipeline.listen.core

import org.pipelinelabs.pipeline.listen.api.Provider

import java.util.concurrent.ConcurrentHashMap

class Providers {
    Map<String, Provider> providers = new ConcurrentHashMap<>()

    void add(Provider provider) {
        if (hasProviderFor(provider.nameForUri)) {
            def msg = "Your trying to add two providers with the same uri name '%s', uri names must be unique"
            throw new IllegalArgumentException(String.format(msg, provider.nameForUri))
        }
        providers.put(provider.nameForUri, provider)
    }

    boolean hasProviderFor(String uriName) {
        providers.containsKey(uriName)
    }
}
