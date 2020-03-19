package life.qbic.portal.sampletracking.app.samples.update

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleTrackingUpdateDataSource {

    def updateSampleLocation(String sampleId, Location updatedLocation)

    def updateSampleStatus(String sampleId, Status updatedStatus)

}
