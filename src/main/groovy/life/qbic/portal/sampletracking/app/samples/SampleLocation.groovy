package life.qbic.portal.sampletracking.app.samples

interface SampleLocation {

    def currentLocation(String sampleId, SampleStatusOutput output)

    def availableLocationsForPersonWithEmail(String email, SampleStatusOutput output)

}