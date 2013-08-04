package nl.ikoodi.pipeline.internal

import nl.ikoodi.pipeline.cli.ServiceLocator
import nl.ikoodi.pipeline.cli.ServiceLookupException
import spock.lang.Specification

class ServiceLocatorSpec extends Specification {

    def "Can locate a single ServiceRegistry"() {
        final locator = new ServiceLocator()
        when:
        final clazz = locator.get(ServiceRegistry)

        then:
        assert clazz instanceof DefaultServiceRegistry
    }

    def "Throws a ServiceLookupException if no implementation can be found"() {
        final locator = new ServiceLocator()
        when:
        locator.get(ServiceLocator)

        then:
        thrown(ServiceLookupException)
    }
}
