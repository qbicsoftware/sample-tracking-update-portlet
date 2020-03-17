package life.qbic.portal.sampletracking.app.samples

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status

/**
 *
 */
interface SampleUpdate {


    def setSampleStatusForSample(Status sampleStatus, String sampleId)

    def setCurrentLocationforSample(Location location, String sampleId)

}