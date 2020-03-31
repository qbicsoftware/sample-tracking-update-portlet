package life.qbic.portal.sampletracking.web.presenters

import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateOutput
import life.qbic.portal.sampletracking.web.ViewModel

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
class SampleListPresenter implements SampleTrackingUpdateOutput{

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
}
