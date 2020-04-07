package life.qbic.portal.sampletracking.trackinginformation.query.locations

interface QueryAvailableLocationsInput {

    /**
     * Queries the sample tracking service for available locations.
     * @param email
     * @return
     */
    def availableLocationsForPerson(String email)

}