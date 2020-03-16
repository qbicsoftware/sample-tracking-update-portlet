package life.qbic.portal.sampletracking.app.samples

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status

/**
 *
 */
interface SampleUpdate {


    // We do not need an output interface, as the method will
    // throw an exception if something goes wrong
    def setSampleStatusForSample(Status sampleStatus, String sampleId) throws SampleTrackingUpdateException

    // We do not need an output interface, as the method will
    // throw an exception if something goes wrong
    def setCurrentLocationforSample(Location location, String sampleId) throws SampleTrackingUpdateException

}