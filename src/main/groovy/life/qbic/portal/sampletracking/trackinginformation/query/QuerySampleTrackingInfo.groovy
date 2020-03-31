package life.qbic.portal.sampletracking.trackinginformation.query

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample

@Log4j2
class QuerySampleTrackingInfo implements SampleTrackingQueryInput {

    final SampleTrackingQueryDataSource sampleTrackingInformation

    private SampleListOutput sampleStatusOutput
    private AvailableLocationsOutput availableLocationsOutput

    private QuerySampleTrackingInfo() {
        // prevent default constructor
        // Avoids accidently default constructor call from within the class
        throw new AssertionError() 
    }

    QuerySampleTrackingInfo(SampleTrackingQueryDataSource sampleTrackingInformation, SampleListOutput sampleStatusOutput, AvailableLocationsOutput availableLocationsOutput) {
        this.sampleTrackingInformation = sampleTrackingInformation
        this.sampleStatusOutput = sampleStatusOutput
        this.availableLocationsOutput = availableLocationsOutput
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
            this.availableLocationsOutput.updateAvailableLocations(locationsForPerson)
        } catch (SampleTrackingQueryException e) {
            log.error e
            this.availableLocationsOutput.invokeOnError "Could not get available locations for person $email"
        }

    }
}
