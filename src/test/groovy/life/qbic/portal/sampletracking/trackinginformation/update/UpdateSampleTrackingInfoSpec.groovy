package life.qbic.portal.sampletracking.trackinginformation.update

import life.qbic.datamodel.people.Address
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import spock.lang.Specification

/**
 * <h1>Tests for the {@link UpdateSampleTrackingInfo}</h1>
 *
 * <p>Specifies the behaviour for life.qbic.portal.sampletracking.trackinginformation.update.UpdateSampleTrackingInfo.</p>
 *
 * @since 1.0.3
 */
class UpdateSampleTrackingInfoSpec extends Specification {

    SampleTrackingUpdateDataSource dataSource = Stub(SampleTrackingUpdateDataSource)
    SampleTrackingUpdateOutput output = Mock(SampleTrackingUpdateOutput)
    private Address address = new Address().affiliation("TestAffili")
            .country("No Man's Land")
            .street("FastLane 007")
            .zipCode(1234)
    Location location = new Location().address(address)
            .responsiblePerson("Nobody")
            .responsibleEmail("No@body.com")
            .status(Status.SAMPLE_RECEIVED)
            .name("Test location")
            .arrivalDate(new Date())
            .forwardDate(null)
    /**
     * @since 1.0.3
     */
    def "UpdateMultipleSampleLocations does nothing on empty input"() {
        given: "a empty request"
        Map<String, Location> request = new HashMap<>()
        when:
        SampleTrackingUpdateInput useCase = new UpdateSampleTrackingInfo(dataSource, output)
        useCase.updateMultipleSampleLocations(request)
        then:
        noExceptionThrown()
        1 * output.updateFinished({ Collection collection -> collection.size() == 0 },
                { Collection collection -> collection.size() == 0 })
        0 * output.updateFinished(_ as String)
        0 * output.invokeOnError(_)
    }

    def "UpdateMultipleSampleLocations fails for invalid and succeedes for valid sample codes"() {
        given: "a location"

        and: "valid sample codes"
        Collection<String> validCodes = ["QABCD001", "QABCD002"]
        dataSource.updateSampleLocation("QABCD001", location) >> void
        dataSource.updateSampleLocation("QABCD002", location) >> void

        and: "invalid sample codes"
        Collection<String> invalidCodes = ["THIS_IS_A_TEST", "QINVALID01", "QINVALID02"]
        dataSource.updateSampleLocation("THIS_IS_A_TEST", location) >> { throw new SampleTrackingUpdateException("expected failure") }
        dataSource.updateSampleLocation("QINVALID01", location) >> { throw new SampleTrackingUpdateException("expected failure") }
        dataSource.updateSampleLocation("QINVALID02", location) >> { throw new SampleTrackingUpdateException("expected failure") }

        and: "a request containing those codes and location"
        Map<String, Location> request = new HashMap<>()
        validCodes.forEach({ request.put(it, location) })
        invalidCodes.forEach({ request.put(it, location) })

        when: "the update is attempted"
        SampleTrackingUpdateInput useCase = new UpdateSampleTrackingInfo(dataSource, output)
        useCase.updateMultipleSampleLocations(request)

        then: "no exception is thrown"
        noExceptionThrown()
        and: "the user is informed with the correct counts"
        1 * output.updateFinished({ (it.toSorted() == validCodes.toSorted()) },
                { it.toSorted() == invalidCodes.toSorted() })
        0 * output.updateFinished(_ as String)
        0 * output.invokeOnError(_)
    }

    def "UpdateMultipleSampleLocations fails for unexpected exception"() {
        given: "a location"

        and: "valid sample codes"
        Collection<String> validCodes = ["QABCD001", "QABCD002"]

        and: "a request containing those codes and location"
        Map<String, Location> request = new HashMap<>()
        validCodes.forEach({ request.put(it, location) })
        and: "a data source that always throws an Exception"
        dataSource.updateSampleLocation(_ as String, location) >> { throw new Exception("test exception") }
        dataSource.updateSampleLocation(_ as String, _ as Location) >> { throw new IllegalArgumentException("Unexpected call") }

        when: "the update is attempted"
        SampleTrackingUpdateInput useCase = new UpdateSampleTrackingInfo(dataSource, output)
        useCase.updateMultipleSampleLocations(request)

        then: "the user is informed about the failure"
        0 * output.updateFinished(_ as String)
        0 * output.invokeOnError(_)
        1 * output.updateFinished({ it.size() == 0 },
                { it.size() == request.keySet().size() })
    }
}
