package life.qbic.portal.sampletracking.io

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleTrackingUpdate {

    updateLocationForSample(Location updatedLocation, String sampleId) throws SampleTrackingUpdateException;

    updateStatusForSample(Status updatedStatus, String sampleId) throws SampleTrackingUpdateException;

}
