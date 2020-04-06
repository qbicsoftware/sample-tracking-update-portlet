package life.qbic.portal.sampletracking.web.presenters

import life.qbic.datamodel.samples.Location
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsOutput
import life.qbic.portal.sampletracking.web.ViewModel

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
class ControlElementsPresenter implements QueryAvailableLocationsOutput {

    final private ViewModel viewModel
    private ControlElementsPresenter() {
        throw new AssertionError("Default constructor cannot be instantiated.")
    }

    ControlElementsPresenter(ViewModel viewModel) {
        this.viewModel = viewModel
    }

    @Override
    def updateAvailableLocations(List<Location> locations) {
        this.viewModel.availableLocations.addAll(locations ?: [])
    }

    @Override
    def invokeOnError(String msg) {
        this.viewModel.failureNotifications.add(msg)
    }
}
