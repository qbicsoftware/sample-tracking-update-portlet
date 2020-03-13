package life.qbic.portal.sampletracking.io

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.services.Service

// Noninstantiable utility class
class SampleTracker {

    // Suppress default constructor for noninstantiability
    private SampleTracker(){
        throw new AssertionError()
    }

    static SampleTrackingUpdate createSampleTrackingUpdate(Service trackingService) {
        new SampleTrackingCenter(trackingService: trackingService)
    }

    static SampleTrackingInformation createSampleTrackingInformation(Service trackingService) {
        new SampleTrackingCenter(trackingService: trackingService)
    }

    class SampleTrackingCenter implements SampleTrackingInformation, SampleTrackingUpdate {

        private final Service trackingService

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
