package life.qbic.portal.sampletracking.app.samples.update

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

@Log4j2
class UpdateSampleTrackingInfo implements SampleUpdate{

    final SampleTrackingUpdate sampleTrackingUpdate
    private SampleUpdateOutput sampleUpdateOutput

    private UpdateSampleTrackingInfo(){

    }

    UpdateSampleTrackingInfo(SampleTrackingUpdate sampleTrackingUpdate) {
        this.sampleTrackingUpdate = sampleTrackingUpdate
    }

    UpdateSampleTrackingInfo(SampleTrackingUpdate sampleTrackingUpdate, SampleUpdateOutput sampleUpdateOutput) {
        this(sampleTrackingUpdate)
        injectSampleUpdateOutput(sampleUpdateOutput)
    }

    @Override
    def setSampleStatus(String sampleId, Status sampleStatus) {
        try {
            this.sampleTrackingUpdate.updateSampleStatus(sampleId, sampleStatus)
        } catch (SampleTrackingUpdateException e) {
            log.error e
            this.sampleUpdateOutput.invokeOnError"Could not update status for sample $sampleId."
        }
    }

    @Override
    def setCurrentSampleLocation(String sampleId, Location location) {
       try {
           this.sampleTrackingUpdate.updateSampleLocation(sampleId, location)
       } catch (SampleTrackingUpdateException e) {
           log.error e
           this.sampleUpdateOutput.invokeOnError "Could not update location for sample $sampleId."

       }
    }

    @Override
    void injectSampleUpdateOutput(SampleUpdateOutput sampleUpdateOutput) {
        if(this.sampleUpdateOutput) {
            log.warn("Tried to overwrite {}. Skipping.", this.sampleUpdateOutput)
        } else {
            this.sampleUpdateOutput = sampleUpdateOutput
        }
    }
}
