package life.qbic.portal.sampletracking.io

import io.micronaut.http.client.HttpClient
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.app.samples.SampleTrackingInformation
import life.qbic.portal.sampletracking.app.samples.SampleTrackingUpdate
import life.qbic.portal.sampletracking.app.samples.SampleTrackingUpdateException

// Noninstantiable utility class
class SampleTracker {

    // Suppress default constructor for noninstantiability
    private SampleTracker(){
        throw new AssertionError()
    }

    static SampleTrackingUpdate createSampleTrackingUpdate(HttpClient client,
                                                           ServiceCredentials serviceCredentials) {
        new SampleTrackingCenter(client, serviceCredentials)
    }

    static SampleTrackingInformation createSampleTrackingInformation(HttpClient client,
                                                                     ServiceCredentials serviceCredentials) {
        new SampleTrackingCenter(client, serviceCredentials)
    }

    static class SampleTrackingCenter implements SampleTrackingInformation, SampleTrackingUpdate {

        private final HttpClient client

        private final ServiceCredentials credentials

        SampleTrackingCenter(HttpClient client, ServiceCredentials credentials) {
            this.client = client
            this.credentials = credentials
        }

        @Override
        Location currentLocationForSample(String sampleId) {
            return null
        }

        @Override
        List<Location> availableLocations() {
            return null
        }

        @Override
        def updateLocationForSample(Location updatedLocation, String sampleId) throws SampleTrackingUpdateException {
            return null
        }

        @Override
        def updateStatusForSample(Status updatedStatus, String sampleId) throws SampleTrackingUpdateException {
            return null
        }
    }

}
