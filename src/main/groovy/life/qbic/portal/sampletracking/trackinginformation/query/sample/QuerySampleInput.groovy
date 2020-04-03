package life.qbic.portal.sampletracking.trackinginformation.query.sample

interface QuerySampleInput {
    /**
     * This method returns a new sample object with the provided code and current location.
     * @param sampleId
     * @return A sample object only containing the current location and the sample code
     */
    def querySampleById(String sampleId)
}