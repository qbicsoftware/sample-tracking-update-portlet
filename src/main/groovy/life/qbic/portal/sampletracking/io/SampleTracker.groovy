package life.qbic.portal.sampletracking.io

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.services.Service

class SampleTracker {

    private static SampleTrackingCenter INSTANCE

    private SampleTracker(){

    }

    static SampleTrackingUpdate getSampleTrackingUpdate(Service trackingService) {
        if ( !INSTANCE ) {
            INSTANCE = new SampleTrackingCenter(trackingService: trackingService)
        }
        return INSTANCE
    }

    static SampleTrackingInformation getSampleTrackingInformation(Service trackingService) {
        if ( !INSTANCE ) {
            INSTANCE = new SampleTrackingCenter(trackingService: trackingService)
        }
        return INSTANCE
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
