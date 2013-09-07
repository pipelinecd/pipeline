package org.pipelinelabs.pipeline.listen.core

import org.pipelinelabs.pipeline.listen.api.Notification
import org.pipelinelabs.pipeline.listen.api.Provider
import spock.lang.Specification

class ProvidersSpec extends Specification {
    private Providers providers = new Providers()

    def 'Can add multiple providers'() {
        def providerNames = ['name1', 'name2']
        when:
        providerNames.each { name ->
            providers.add(createProvider(name))
        }
        then:
        providerNames.each { name ->
            assert providers.hasProviderFor(name)
        }
    }

    def 'Providers must have unique uri names'() {
        def providerNames = ['name', 'name']
        when:
        providerNames.each { name ->
            providers.add(createProvider(name))
        }
        then:
        thrown(IllegalArgumentException)
    }

    private createProvider(String name) {
        new Provider<TestNotification>() {
            @Override
            String getName() {
                name
            }

            @Override
            String getNameForUri() {
                name
            }

            @Override
            Class<TestNotification> supports() {
                TestNotification
            }
        }
    }

    class TestNotification implements Notification {}
}
