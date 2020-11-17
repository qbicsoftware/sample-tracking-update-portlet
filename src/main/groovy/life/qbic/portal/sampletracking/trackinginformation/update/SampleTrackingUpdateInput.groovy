package life.qbic.portal.sampletracking.trackinginformation.update

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleTrackingUpdateInput {

    /**
     * This method shall be called, when the sample status is to be updated.
     *
     * @deprecated
     * Do not use this method anymore, it will get removed in future releases.
     * Please use the method ${@link #setCurrentSampleLocation} to also update the status.
     *
     * @param sampleId The sample identifier / code
     * @param sampleStatus The sample status
     * @return
     */
    @Deprecated
    def setSampleStatus(String sampleId, Status sampleStatus)

    /**
     * This method shall be called, when the sample location and/or status needs to be updated.
     * @param sampleId The sample identifier / code
     * @param location The sample location (with potential updated status)
     * @return
     */
    def setCurrentSampleLocation(String sampleId, Location location)
}