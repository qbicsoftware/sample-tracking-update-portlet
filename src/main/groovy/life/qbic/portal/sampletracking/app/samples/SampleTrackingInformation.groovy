package life.qbic.portal.sampletracking.app.samples

import life.qbic.datamodel.samples.Location

interface SampleTrackingInformation {

    Location currentLocationForSample(String sampleId)

    List<Location> availableLocationsForPersonWithEmail(String email)

}