package life.qbic.portal.sampletracking.app.samples.update

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

@Log4j2
class UpdateSampleTrackingInfo implements SampleUpdate{

    final SampleTrackingUpdate sampleTrackingUpdate

    UpdateSampleTrackingInfo(){
        new AssertionError()
    }

    UpdateSampleTrackingInfo(SampleTrackingUpdate sampleTrackingUpdate) {
        this.sampleTrackingUpdate = sampleTrackingUpdate
        this.output = output
    }

    @Override
    def setSampleStatus(String sampleId, Status sampleStatus, SampleUpdateOutput output) {
        try {
            this.sampleTrackingUpdate.updateSampleStatus(sampleId, sampleStatus)
        } catch (SampleTrackingUpdateException e) {
            log.error e
            output.invokeOnError"Could not update status for sample $sampleId."
        }
    }

    @Override
    def setCurrentSampleLocation(String sampleId, Location location, SampleUpdateOutput output) {
       try {
           this.sampleTrackingUpdate.updateSampleLocation(sampleId, location)
       } catch (SampleTrackingUpdateException e) {
           log.error e
           output.invokeOnError "Could not update location for sample $sampleId."

       }
    }
}