package life.qbic.portal.sampletracking.web


import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample

class ViewModel {

    final ObservableList samples
    final ObservableList availableLocations
    final ObservableList successNotifications
    final ObservableList failureNotifications

    ViewModel() {
        this(new ArrayList<Sample>(), new ArrayList<Location>(), new ArrayList<String>(), new ArrayList<String>())
    }

    ViewModel(List<Sample> samples, List<Location> availableLocations, List<String> successNotifications,
              List<String> failureNotifications) {
        this.samples = new ObservableList(samples)
        this.availableLocations = new ObservableList(availableLocations)
        this.successNotifications = new ObservableList(successNotifications)
        this.failureNotifications = new ObservableList(failureNotifications)
    }

}
