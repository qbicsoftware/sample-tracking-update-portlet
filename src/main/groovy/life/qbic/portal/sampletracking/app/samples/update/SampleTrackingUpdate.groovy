package life.qbic.portal.sampletracking.app.samples.update

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleTrackingUpdate {

    def updateLocationForSample(Location updatedLocation, String sampleId);

    def updateStatusForSample(Status updatedStatus, String sampleId);

}
