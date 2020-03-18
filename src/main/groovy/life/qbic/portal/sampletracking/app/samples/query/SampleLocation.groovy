package life.qbic.portal.sampletracking.app.samples.query

interface SampleLocation {

    def currentLocation(String sampleId)

    def availableLocationsForPerson(String email)

}