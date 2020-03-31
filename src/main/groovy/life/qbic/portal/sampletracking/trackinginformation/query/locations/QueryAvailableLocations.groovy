package life.qbic.portal.sampletracking.trackinginformation.query.locations

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryDataSource
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryException
import life.qbic.portal.sampletracking.trackinginformation.query.sample.QuerySampleOutput

@Log4j2
class QueryAvailableLocations implements QueryAvailableLocationsInput {

    final SampleTrackingQueryDataSource sampleTrackingDataSource

    private QueryAvailableLocationsOutput availableLocationsOutput

    private QueryAvailableLocations() {
        // Avoids accidental default constructor call from within the class
        throw new AssertionError() 
    }

    QueryAvailableLocations(SampleTrackingQueryDataSource dataSource, QueryAvailableLocationsOutput availableLocationsOutput) {
        this.sampleTrackingDataSource = dataSource
        this.availableLocationsOutput = availableLocationsOutput
    }

    @Override
    def availableLocationsForPerson(String email) {
        try {
            List<Location> locationsForPerson = sampleTrackingDataSource.availableLocationsForPerson(email)
            this.availableLocationsOutput.updateAvailableLocations(locationsForPerson)
        } catch (SampleTrackingQueryException e) {
            log.error e
            this.availableLocationsOutput.invokeOnError "Could not get available locations for person $email"
        }

    }
}
