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
    void updateFinished() {
        viewModel.samples.clear()
    }

    @Override
    def publishSample(Sample sample) {
        this.viewModel.samples.add(sample)
    }
}
