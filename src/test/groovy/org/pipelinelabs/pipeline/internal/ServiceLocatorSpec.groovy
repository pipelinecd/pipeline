package org.pipelinelabs.pipeline.internal

import org.pipelinelabs.pipeline.cli.ServiceLocator
import org.pipelinelabs.pipeline.cli.ServiceLookupException
import spock.lang.Specification

class ServiceLocatorSpec extends Specification {

    def "Can locate a single implementation instance"() {
        final locator = new ServiceLocator()
        when:
        final clazz = locator.find(ServiceExpectedToBeImplementedByOneClass)

        then:
        assert clazz instanceof SingleImplementation
    }

    def "Can locate multiple implementation instances"() {
        final locator = new ServiceLocator()
        when:
        final clazz = locator.findAll(ServiceExpectedToBeImplementedByMultipleClasses)

        then:
        assert clazz instanceof Set
        assert clazz.size() == 3
    }

    def "Throws a ServiceLookupException if no implementation can be found"() {
        final locator = new ServiceLocator()
        when:
        locator.find(ServiceWithoutAvailableImplementation)

        then:
        def e = thrown(ServiceLookupException)
        assert e.message.contains(ServiceWithoutAvailableImplementation.class.simpleName)
    }

    def "Throws a ServiceLookupException when expecting one implementation while finding multiple"() {
        final locator = new ServiceLocator()
        when:
        locator.find(ServiceExpectedToBeImplementedByMultipleClasses)

        then:
        def e = thrown(ServiceLookupException)
        assert e.message.contains(ServiceExpectedToBeImplementedByMultipleClasses.class.simpleName)
    }

}

interface ServiceExpectedToBeImplementedByOneClass {}
class SingleImplementation implements ServiceExpectedToBeImplementedByOneClass {}

interface ServiceExpectedToBeImplementedByMultipleClasses {}
class MultipleImplementationsFirstImpl implements ServiceExpectedToBeImplementedByMultipleClasses {}
class MultipleImplementationsSecondImpl implements ServiceExpectedToBeImplementedByMultipleClasses {}
class MultipleImplementationsThirdImpl implements ServiceExpectedToBeImplementedByMultipleClasses {}

interface ServiceWithoutAvailableImplementation {}
