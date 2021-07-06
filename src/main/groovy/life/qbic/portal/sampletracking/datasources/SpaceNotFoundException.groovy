package life.qbic.portal.sampletracking.datasources

/**
 * <p>To be thrown whenever no Spaces could be found when spaces were expected to be present.</p>
 *
 * @since 1.3.0
 */
class SpaceNotFoundException extends RuntimeException{
    SpaceNotFoundException(String message) {
        super(message)
    }
}
