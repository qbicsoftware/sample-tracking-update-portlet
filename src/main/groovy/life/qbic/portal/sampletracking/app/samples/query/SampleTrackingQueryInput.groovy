package life.qbic.portal.sampletracking.app.samples.query

interface SampleTrackingQueryInput {

    def currentLocation(String sampleId)

    def getSampleById(String sampleId)

    def availableLocationsForPerson(String email)

}