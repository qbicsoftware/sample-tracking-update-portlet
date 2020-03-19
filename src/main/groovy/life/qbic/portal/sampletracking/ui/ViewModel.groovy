package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Notification
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.app.samples.list.ModifySampleListOutput
import life.qbic.portal.sampletracking.app.samples.query.SampleStatusOutput
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdateOutput

//TODO: rename class to something other than implementation
class ViewModel implements SampleStatusOutput, SampleUpdateOutput, ModifySampleListOutput,
        SampleImportModel, SampleListModel, SampleModifyControlsModel, PortletViewModel{
    final private ObservableList samples
    final private ObservableList availableLocations
    final private ObservableList notifications

    ViewModel(ObservableList samples, ObservableList availableLocations, ObservableList notifications) {
        this.samples = samples
        this.availableLocations = availableLocations
        this.notifications = notifications
    }

    @Override
    void addSamples(Sample... samples) {
        this.samples.addAll(samples)
    }

    @Override
    void removeSamples(Sample... samples) {
        this.samples.removeAll(samples)
    }

    @Override
    void clearSamples() {
        this.samples.clear()
    }

    @Override
    def updateAvailableLocations(List<Location> locations) {
        this.availableLocations.clear()
        this.availableLocations.addAll(locations)

    }

    @Override
    def updateCurrentLocation(String sampleId, Location location) {
        return null
    }

    @Override
    def invokeOnError(String msg) {
        return null
    }

    @Override
    ObservableList requestSampleList() {
        return this.samples
    }

    @Override
    ObservableList requestAvailableLocations() {
        return this.availableLocations
    }

    @Override
    ObservableList requestNotifications() {
        return null
    }
}
