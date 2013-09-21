package org.pipelinelabs.pipeline.pipe.internal

import org.pipelinelabs.pipeline.pipe.cli.ServiceLocator
import org.pipelinelabs.pipeline.pipe.cli.ServiceLookupException
import spock.lang.Specification

class ServiceLocatorSpec extends Specification {
    final locator = new ServiceLocator()

    def "Can locate a single implementation instance"() {
        when:
        def clazz = locator.find(ServiceExpectedToBeImplementedByOneClass)

        then:
        assert clazz instanceof SingleImplementation
    }

    def "Can locate multiple implementation instances"() {
        when:
        def clazz = locator.findAll(ServiceExpectedToBeImplementedByMultipleClasses)

        then:
        assert clazz instanceof Set
        assert clazz.size() == 3
    }

    def "Throws a ServiceLookupException if no implementation can be found"() {
        when:
        locator.find(ServiceWithoutAvailableImplementation)

        then:
        def e = thrown(ServiceLookupException)
        assert e.message.contains(ServiceWithoutAvailableImplementation.class.simpleName)
    }

    def "Throws a ServiceLookupException when expecting one implementation while finding multiple"() {
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
