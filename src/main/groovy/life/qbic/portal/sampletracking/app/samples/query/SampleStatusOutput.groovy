package life.qbic.portal.sampletracking.app.samples.query

import life.qbic.datamodel.samples.Location

interface SampleStatusOutput {

    def availableLocations(List<Location> locations)

    def currentLocation(Location location)

    def invokeOnError(String msg)

}