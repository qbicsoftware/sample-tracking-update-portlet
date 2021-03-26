package life.qbic.portal.sampletracking.trackinginformation.update

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

@Log4j2
class UpdateSampleTrackingInfo implements SampleTrackingUpdateInput {

    final SampleTrackingUpdateDataSource sampleTrackingUpdateDataSource
    private SampleTrackingUpdateOutput sampleUpdateOutput

    UpdateSampleTrackingInfo(SampleTrackingUpdateDataSource sampleTrackingUpdateDataSource, SampleTrackingUpdateOutput sampleUpdateOutput) {
        this.sampleTrackingUpdateDataSource = sampleTrackingUpdateDataSource
        this.sampleUpdateOutput = sampleUpdateOutput
    }

    @Override
    def setSampleStatus(String sampleId, Status sampleStatus) {
        try {
            this.sampleTrackingUpdateDataSource.updateSampleStatus(sampleId, sampleStatus)
            sampleUpdateOutput.updateFinished(sampleId)
        } catch (SampleTrackingUpdateException e) {
            log.error e
            this.sampleUpdateOutput.invokeOnError "Could not update status for sample $sampleId."
        }
    }

    @Override
    def setCurrentSampleLocation(String sampleId, Location location) {
        try {
            updateSampleLocation(sampleId, location)
            sampleUpdateOutput.updateFinished(sampleId)
        } catch (SampleTrackingUpdateException e) {
            log.error e
            this.sampleUpdateOutput.invokeOnError "Could not update location for sample $sampleId."

        }
    }

    /**
     * This method shall be called, when multiple samples and their current locations are updated
     * @param updateInformation a map containing sample codes as keys and the desired location as value
     * @since 1.3.0
     */
    @Override
    void updateMultipleSampleLocations(Map<String, Location> updateInformation) {
        Collection<String> successfullyUpdated = new ArrayList<>()
        Collection<String> updateFailed = new ArrayList<>()
        for (Map.Entry<String, Location> request in updateInformation.entrySet()) {
            String sampleId = request.getKey()
            Location location = request.getValue()
            try {
                updateSampleLocation(sampleId, location)
            } catch (SampleTrackingUpdateException updateException) {
                log.error("Could not update sample $sampleId to location $location")
                log.debug("Could not update sample $sampleId to location $location", updateException)
                updateFailed.add(sampleId)
            }
        }
        sampleUpdateOutput.updateFinished(successfullyUpdated, updateFailed)
    }


    /**
     *
     * @param sampleId the sample identifier
     * @param location the new location the sample identifier should be set to
     * @throws SampleTrackingUpdateException forwards exceptions directly from the DataSource
     * @see life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateDataSource#updateSampleLocation(java.lang.String, life.qbic.datamodel.samples.Location)
     * @since 1.3.0
     */
    private void updateSampleLocation(String sampleId, Location location) throws SampleTrackingUpdateException {
        this.sampleTrackingUpdateDataSource.updateSampleLocation(sampleId, location)
    }
}
