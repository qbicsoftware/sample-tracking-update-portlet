package life.qbic.portal.sampletracking.web.presenters

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryOutput

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
class ControlElementsPresenter implements SampleTrackingQueryOutput {


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
        return null
    }
}
