package life.qbic.portal.sampletracking.io

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleTrackingUpdate {

    def updateLocationForSample(Location updatedLocation, String sampleId) throws SampleTrackingUpdateException;

    def updateStatusForSample(Status updatedStatus, String sampleId) throws SampleTrackingUpdateException;

}
