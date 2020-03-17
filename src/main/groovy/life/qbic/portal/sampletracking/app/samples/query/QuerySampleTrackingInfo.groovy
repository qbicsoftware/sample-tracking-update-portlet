package life.qbic.portal.sampletracking.app.samples.query

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location

@Log4j2
class QuerySampleTrackingInfo implements SampleLocation{

    final SampleTrackingInformation sampleTrackingInformation

    QuerySampleTrackingInfo(){
        new AssertionError()
    }

    QuerySampleTrackingInfo(SampleTrackingInformation sampleTrackingInformation) {
        this.sampleTrackingInformation = sampleTrackingInformation
    }

    @Override
    def currentLocation(String sampleId, SampleStatusOutput output) {
        try {
            Location location = sampleTrackingInformation.currentLocationForSample(sampleId)
            output.updateCurrentLocation(location)
        } catch (SampleTrackingQueryException e) {
            log.error e
            output.invokeOnError "Could not get current location for sample $sampleId"
        }
    }


    @Override
    def availableLocationsForPerson(String email, SampleStatusOutput output) {
        try {
            List<Location> locationsForPerson = sampleTrackingInformation.availableLocationsForPerson(email)
            output.updateAvailableLocations(locationsForPerson)
        } catch (SampleTrackingQueryException e) {
            log.error e
            output.invokeOnError "Could not get available locations for person $email"
        }

    }
}
