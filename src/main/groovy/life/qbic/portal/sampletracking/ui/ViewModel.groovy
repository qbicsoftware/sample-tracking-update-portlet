package life.qbic.portal.sampletracking.ui


import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.app.samples.list.ModifySampleListOutput
import life.qbic.portal.sampletracking.app.samples.query.SampleTrackingQueryOutput

//TODO: rename class to something other than implementation
class ViewModel {
    final ObservableList samples
    final ObservableList availableLocations
    final ObservableList notifications

    ViewModel(List<Sample> samples, List<Location> availableLocations, List<String> notifications) {
        this.samples = new ObservableList(samples)
        this.availableLocations = new ObservableList(availableLocations)
        this.notifications = new ObservableList(notifications)
    }

}
