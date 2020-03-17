package life.qbic.portal.sampletracking.app.samples

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

@Log4j2
class UpdateSampleTrackingInfo implements SampleUpdate{

    final SampleTrackingUpdate sampleTrackingUpdate

    final SampleUpdateOutput output

    UpdateSampleTrackingInfo(){
        new AssertionError()
    }

    UpdateSampleTrackingInfo(SampleTrackingUpdate sampleTrackingUpdate, SampleUpdateOutput output) {
        this.sampleTrackingUpdate = sampleTrackingUpdate
        this.output = output
    }

    @Override
    def setSampleStatusForSample(Status sampleStatus, String sampleId) {
        try {
            this.sampleTrackingUpdate.updateStatusForSample(sampleStatus, sampleId)
        } catch (SampleTrackingUpdateException e) {
            log.error e
            output.invokeOnError"Could not update status for sample $sampleId."
        }
    }

    @Override
    def setCurrentLocationforSample(Location location, String sampleId) {
       try {
           this.sampleTrackingUpdate.updateLocationForSample(location, sampleId)
       } catch (SampleTrackingUpdateException e) {
           log.error e
           output.invokeOnError "Could not update location for sample $sampleId."

       }
    }
}
