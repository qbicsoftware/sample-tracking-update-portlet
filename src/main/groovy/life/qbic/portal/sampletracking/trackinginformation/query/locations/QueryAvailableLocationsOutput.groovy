package life.qbic.portal.sampletracking.trackinginformation.query.locations

import life.qbic.datamodel.samples.Location
import life.qbic.portal.sampletracking.trackinginformation.UseCaseOutput

interface QueryAvailableLocationsOutput extends UseCaseOutput{
    /**
     * Update the locations available to the user
     * @param locations
     * @return
     */
    def updateAvailableLocations(List<Location> locations)
}