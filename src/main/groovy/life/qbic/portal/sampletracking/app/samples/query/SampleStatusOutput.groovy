package life.qbic.portal.sampletracking.app.samples.query

import life.qbic.datamodel.samples.Location

interface SampleStatusOutput {

    def updateAvailableLocations(List<Location> locations)

    def updateCurrentLocation(String sampleId, Location location)

    def invokeOnError(String msg)

}