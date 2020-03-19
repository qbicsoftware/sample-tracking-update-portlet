package life.qbic.portal.sampletracking.app.samples.query

import life.qbic.datamodel.samples.Location

interface SampleTrackingQueryDataSource {

    Location currentSampleLocation(String sampleId)

    List<Location> availableLocationsForPerson(String emailAddress)

}