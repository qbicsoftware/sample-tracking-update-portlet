package life.qbic.portal.sampletracking.web.presenters

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.AvailableLocationsOutput
import life.qbic.portal.sampletracking.trackinginformation.query.SampleListOutput

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
class ControlElementsPresenter implements AvailableLocationsOutput {


    @Override
    def updateAvailableLocations(List<Location> locations) {
        return null
    }

    @Override
    def invokeOnError(String msg) {
        return null
    }
}
