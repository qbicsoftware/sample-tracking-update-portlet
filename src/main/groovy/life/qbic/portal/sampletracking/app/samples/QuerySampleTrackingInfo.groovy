package life.qbic.portal.sampletracking.app.samples

import life.qbic.datamodel.samples.Location

class QuerySampleTrackingInfo implements SampleTrackingInformation{

    QuerySampleTrackingInfo()


    @Override
    Location currentLocationForSample(String sampleId) {
        return null
    }

    @Override
    List<Location> availableLocations() {
        return null
    }
}
