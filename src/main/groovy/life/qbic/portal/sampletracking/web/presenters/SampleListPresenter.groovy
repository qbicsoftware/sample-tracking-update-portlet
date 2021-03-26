package life.qbic.portal.sampletracking.web.presenters

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.sample.QuerySampleOutput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateOutput
import life.qbic.portal.sampletracking.web.ViewModel

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
@Log4j2
class SampleListPresenter implements SampleTrackingUpdateOutput, QuerySampleOutput{

    private final ViewModel viewModel

    SampleListPresenter(ViewModel viewModel) {
        this.viewModel = viewModel
    }


    @Override
    def invokeOnError(String msg) {
        viewModel.failureNotifications.add(msg)
    }

    @Override
    void updateFinished(String sampleId) {
        viewModel.successNotifications.add("Sample $sampleId has been updated." )
        viewModel.samples.removeAll {  sample -> (sample as Sample).code == sampleId }
    }

    /**
     * Signals that the update for process is finished and includes information on the samples updated successfully
     * @param successfulCodes the sample codes for which the update was successful
     * @param failedCodes the sample codes for which the update was unsuccessful
     * @since 1.3.0
     */
    @Override
    void updateFinished(Collection<String> successfulCodes, Collection<String> failedCodes) {
        viewModel.successNotifications.add("Updated ${successfulCodes.size()} samples.")
        if (failedCodes) {
            viewModel.failureNotifications.add("Could not update\n" + failedCodes.join("\n\t"))
        }
        viewModel.samples.removeAll {  sample -> (sample as Sample).code in successfulCodes }
    }

    @Override
    def publishSample(Sample sample) {
        this.viewModel.samples.add(sample)
    }
}
