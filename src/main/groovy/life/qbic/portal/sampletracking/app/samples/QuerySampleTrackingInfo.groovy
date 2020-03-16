package life.qbic.portal.sampletracking.app.samples

import life.qbic.datamodel.samples.Location

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
        return null
    }

    @Override
    def availableLocationsForPersonWithEmail(String email, SampleStatusOutput output) {
        return null
    }
}
