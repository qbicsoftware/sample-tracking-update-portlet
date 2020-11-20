package life.qbic.portal.sampletracking.trackinginformation.query.locations

interface QueryAvailableLocationsInput {

    /**
     * Queries the sample tracking service for available locations.
     * @param email
     * @param username
     * @return
     */
    def availableLocationsForPerson(String email, String username)

}