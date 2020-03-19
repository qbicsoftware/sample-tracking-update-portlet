package life.qbic.portal.sampletracking.app.samples.update

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

@Log4j2
class UpdateSampleTrackingInfo implements SampleTrackingUpdateInput {

    final SampleTrackingUpdateDataSource sampleTrackingUpdateDataSource
    private SampleTrackingUpdateOutput sampleUpdateOutput

    private UpdateSampleTrackingInfo() {
        // prevent default constructor
        throw new AssertionError()
    }


    UpdateSampleTrackingInfo(SampleTrackingUpdateDataSource sampleTrackingUpdateDataSource, SampleTrackingUpdateOutput sampleUpdateOutput) {
        this.sampleTrackingUpdateDataSource = sampleTrackingUpdateDataSource
        this.sampleUpdateOutput = sampleUpdateOutput
    }

    @Override
    def setSampleStatus(String sampleId, Status sampleStatus) {
        try {
            this.sampleTrackingUpdateDataSource.updateSampleStatus(sampleId, sampleStatus)
        } catch (SampleTrackingUpdateException e) {
            log.error e
            this.sampleUpdateOutput.invokeOnError "Could not update status for sample $sampleId."
        }
    }

    @Override
    def setCurrentSampleLocation(String sampleId, Location location) {
        try {
            this.sampleTrackingUpdateDataSource.updateSampleLocation(sampleId, location)
        } catch (SampleTrackingUpdateException e) {
            log.error e
            this.sampleUpdateOutput.invokeOnError "Could not update location for sample $sampleId."

        }
    }
}
