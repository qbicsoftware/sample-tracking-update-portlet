package life.qbic.portal.sampletracking.app.samples.query

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdateOutput

@Log4j2
class QuerySampleTrackingInfo implements SampleLocation{

    final SampleTrackingInformation sampleTrackingInformation

    private SampleStatusOutput sampleStatusOutput;

    QuerySampleTrackingInfo(){
        new AssertionError()
    }

    QuerySampleTrackingInfo(SampleTrackingInformation sampleTrackingInformation, SampleStatusOutput sampleStatusOutput) {
        this.sampleTrackingInformation = sampleTrackingInformation
        this.sampleStatusOutput = sampleStatusOutput
    }

    @Override
    def currentLocation(String sampleId) {
        try {
            Location location = sampleTrackingInformation.currentSampleLocation(sampleId)
            this.sampleStatusOutput.updateCurrentLocation(sampleId, location)
        } catch (SampleTrackingQueryException e) {
            log.error e
            this.sampleStatusOutput.invokeOnError "Could not get current location for sample $sampleId"
        }
    }


    @Override
    def availableLocationsForPerson(String email) {
        try {
            List<Location> locationsForPerson = sampleTrackingInformation.availableLocationsForPerson(email)
            this.sampleStatusOutput.updateAvailableLocations(locationsForPerson)
        } catch (SampleTrackingQueryException e) {
            log.error e
            this.sampleStatusOutput.invokeOnError "Could not get available locations for person $email"
        }

    }

    @Override
    void injectSampleStatusOutput(SampleStatusOutput sampleStatusOutput) {
        if(this.sampleStatusOutput) {
            log.warn("Tried to overwrite {}. Skipping.", this.sampleStatusOutput)
        } else {
            this.sampleStatusOutput = sampleStatusOutput
        }
    }
}
