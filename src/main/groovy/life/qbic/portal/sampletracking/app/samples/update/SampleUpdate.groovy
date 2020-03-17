package life.qbic.portal.sampletracking.app.samples.update

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleUpdate {

    def setSampleStatusForSample(Status sampleStatus, String sampleId)

    def setCurrentLocationForSample(Location location, String sampleId)

}