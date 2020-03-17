package life.qbic.portal.sampletracking.app.samples.query

interface SampleLocation {

    def currentLocation(String sampleId, SampleStatusOutput output)

    def availableLocationsForPersonWithEmail(String email, SampleStatusOutput output)

}