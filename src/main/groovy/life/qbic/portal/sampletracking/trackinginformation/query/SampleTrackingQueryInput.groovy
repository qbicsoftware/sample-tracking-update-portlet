package life.qbic.portal.sampletracking.trackinginformation.query

interface SampleTrackingQueryInput {

    def querySampleById(String sampleId)

    def availableLocationsForPerson(String email)

}