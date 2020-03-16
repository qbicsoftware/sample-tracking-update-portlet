package life.qbic.portal.sampletracking.app.samples

import life.qbic.datamodel.samples.Location

interface SampleStatusOutput {

    availableLocations(List<Location> locations)

    currentLocation(Location location)

}