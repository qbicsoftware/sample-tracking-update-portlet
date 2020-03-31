package life.qbic.portal.sampletracking.web.presenters

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryOutput
import life.qbic.portal.sampletracking.web.ViewModel

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
class ControlElementsPresenter implements SampleTrackingQueryOutput {

    private final ViewModel viewModel

    ControlElementsPresenter(ViewModel viewModel) {
        this.viewModel = viewModel
    }

    @Override
    def updateAvailableLocations(List<Location> locations) {
        return null
    }

    @Override
    def updateCurrentLocation(String sampleId, Location location) {
        return null
    }

    @Override
    def addSampleToList(Sample sample) {
        return null
    }

    @Override
    def invokeOnError(String msg) {
        viewModel.notifications.add(msg)
    }
}
