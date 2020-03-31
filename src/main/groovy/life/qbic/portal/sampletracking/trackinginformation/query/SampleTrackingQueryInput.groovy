package life.qbic.portal.sampletracking.trackinginformation.query

interface SampleTrackingQueryInput {

    def currentLocation(String sampleId)

    def querySampleById(String sampleId)

    def availableLocationsForPerson(String email)

}