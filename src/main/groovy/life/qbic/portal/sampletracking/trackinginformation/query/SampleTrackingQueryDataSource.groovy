package life.qbic.portal.sampletracking.trackinginformation.query

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample

interface SampleTrackingQueryDataSource {

    Location currentSampleLocation(String sampleId)

    List<Location> availableLocationsForPerson(String emailAddress)
}