package life.qbic.portal.sampletracking.ui

import life.qbic.portal.sampletracking.app.samples.update.SampleTrackingUpdateOutput

import javax.swing.text.View

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
