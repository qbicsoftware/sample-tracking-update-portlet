package life.qbic.portal.sampletracking.web.presenters

import groovy.util.logging.Log4j2
import life.qbic.datamodel.identifiers.TooManySamplesException
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.SampleListOutput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateOutput
import life.qbic.portal.sampletracking.web.ViewModel

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
@Log4j2
class SampleListPresenter implements SampleTrackingUpdateOutput, SampleListOutput{

    private final ViewModel viewModel

    SampleListPresenter(ViewModel viewModel) {
        this.viewModel = viewModel
    }


    @Override
    def invokeOnError(String msg) {
        viewModel.notifications.add(msg)
    }

    @Override
    void updateFinished() {
        viewModel.samples.clear()
    }

    @Override
    def addSampleToList(Sample sample) {
        this.viewModel.samples.add(sample)
    }
}
