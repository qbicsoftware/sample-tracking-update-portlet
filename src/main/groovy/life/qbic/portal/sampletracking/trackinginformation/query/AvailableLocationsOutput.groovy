package life.qbic.portal.sampletracking.trackinginformation.query

import life.qbic.datamodel.samples.Location
import life.qbic.portal.sampletracking.trackinginformation.UseCaseOutput

interface AvailableLocationsOutput extends UseCaseOutput{
    def updateAvailableLocations(List<Location> locations)
}