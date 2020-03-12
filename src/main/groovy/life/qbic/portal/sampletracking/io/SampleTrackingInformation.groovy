package life.qbic.portal.sampletracking.io

import life.qbic.datamodel.samples.Location

interface SampleTrackingInformation {

    Location currentLocationForSample(String sampleId)

    List<Location> availableLocations()

}