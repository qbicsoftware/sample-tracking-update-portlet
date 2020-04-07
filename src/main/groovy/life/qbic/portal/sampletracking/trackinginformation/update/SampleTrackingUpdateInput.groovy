package life.qbic.portal.sampletracking.trackinginformation.update

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleTrackingUpdateInput {

    def setSampleStatus(String sampleId, Status sampleStatus)

    def setCurrentSampleLocation(String sampleId, Location location)
}