package life.qbic.portal.sampletracking.app.samples.query

import life.qbic.datamodel.samples.Location
import life.qbic.portal.sampletracking.app.samples.UseCaseOutput

interface SampleStatusOutput extends UseCaseOutput{

    def updateAvailableLocations(List<Location> locations)

    def updateCurrentLocation(String sampleId, Location location)
}