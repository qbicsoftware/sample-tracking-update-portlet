package life.qbic.portal.sampletracking.trackinginformation.query.sample

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryDataSource
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryException
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsInput
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsOutput

@Log4j2
class QuerySample implements QuerySampleInput {

    final SampleTrackingQueryDataSource sampleTrackingDataSource

    private QuerySampleOutput querySampleOutput

    private QuerySample() {
        // Avoids accidental default constructor call from within the class
        throw new AssertionError()
    }

    QuerySample(SampleTrackingQueryDataSource dataSource, QuerySampleOutput querySampleOutput) {
        this.sampleTrackingDataSource = dataSource
        this.querySampleOutput = querySampleOutput
    }

    @Override
    def querySampleById(String sampleId) {
        try {
            Location sampleLocation = sampleTrackingDataSource.currentSampleLocation(sampleId)
            Sample sample = new Sample()
            sample.setCurrentLocation(sampleLocation)
            sample.setCode(sampleId)
            this.querySampleOutput.publishSample(sample)
        } catch (SampleTrackingQueryException e) {
            log.error("Query ${sampleId} failed.", e)
            this.querySampleOutput.invokeOnError "Could not locate Sample $sampleId in QBiC database"
        }
    }
}
