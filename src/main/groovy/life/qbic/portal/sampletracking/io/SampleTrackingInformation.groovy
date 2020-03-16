package life.qbic.portal.sampletracking.io

import life.qbic.datamodel.samples.Location

interface SampleTrackingInformation {

    Location currentLocationForSample(String sampleId) throws SampleTrackingQueryException

    List<Location> availableLocationsForPersonWithEmail(String emailAdress)

}