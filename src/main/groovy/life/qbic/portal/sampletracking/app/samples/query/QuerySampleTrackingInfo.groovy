package life.qbic.portal.sampletracking.app.samples.query

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample

@Log4j2
class QuerySampleTrackingInfo implements SampleTrackingQueryInput {

    final SampleTrackingQueryDataSource sampleTrackingInformation

    private SampleTrackingQueryOutput sampleStatusOutput

    private QuerySampleTrackingInfo() {
        // prevent default constructor
        // Avoids accidently default constructor call from within the class
        throw new AssertionError() 
    }

    QuerySampleTrackingInfo(SampleTrackingQueryDataSource sampleTrackingInformation, SampleTrackingQueryOutput sampleStatusOutput) {
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
    def querySampleById(String sampleId) {
        try {
            Sample sample = sampleTrackingInformation.retrieveCurrentSample(sampleId)
            this.sampleStatusOutput.addSampleToList(sample)
        } catch (SampleTrackingQueryException e) {
            log.error("Query ${sampleId} failed.", e)
            this.sampleStatusOutput.invokeOnError "Could not locate Sample $sampleId in QBiC database"
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
}
