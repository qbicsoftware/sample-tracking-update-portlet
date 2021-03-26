package life.qbic.portal.sampletracking.trackinginformation.update

import life.qbic.portal.sampletracking.trackinginformation.UseCaseOutput

interface SampleTrackingUpdateOutput extends UseCaseOutput {

    void updateFinished(String sampleId)

    /**
     * Signals that the update for process is finished and includes information on the samples updated successfully
     * @param successfulCodes the sample codes for which the update was successful
     * @param failedCodes the sample codes for which the update was unsuccessful
     * @since 1.3.0
     */
    void updateFinished(Collection<String> successfulCodes, Collection<String> failedCodes)

}