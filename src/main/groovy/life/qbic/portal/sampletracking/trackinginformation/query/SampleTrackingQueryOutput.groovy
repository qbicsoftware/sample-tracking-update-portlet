package life.qbic.portal.sampletracking.trackinginformation.query

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.UseCaseOutput

interface SampleTrackingQueryOutput extends UseCaseOutput {

    def updateAvailableLocations(List<Location> locations)

    def addSampleToList(Sample sample)
}