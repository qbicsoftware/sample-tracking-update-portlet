package life.qbic.portal.sampletracking.ui

import life.qbic.portal.sampletracking.app.samples.update.SampleTrackingUpdateOutput

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
class SampleListPresenter implements SampleTrackingUpdateOutput{

    private final SampleList sampleList

    SampleListPresenter(SampleList sampleList) {
        this.sampleList = sampleList
    }


    @Override
    def invokeOnError(String msg) {

    }

    @Override
    def updateFinished() {
        sampleList.viewModel.requestSampleList().clear()
    }
}
