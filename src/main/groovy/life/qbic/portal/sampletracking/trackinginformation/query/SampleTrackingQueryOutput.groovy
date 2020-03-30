package life.qbic.portal.sampletracking.trackinginformation.query

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.app.samples.UseCaseOutput

interface SampleTrackingQueryOutput extends UseCaseOutput {

    def updateAvailableLocations(List<Location> locations)

    def updateCurrentLocation(String sampleId, Location location)

    def addSampleToList(Sample sample)
}