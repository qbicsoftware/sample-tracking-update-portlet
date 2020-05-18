package life.qbic.portal.sampletracking.trackinginformation.query

class OpenbisAuthorizationException extends RuntimeException {

    OpenbisAuthorizationException() {
        super()
    }

    OpenbisAuthorizationException(String msg) {
        super(msg)
    }

    OpenbisAuthorizationException(Throwable t) {
        super(t)
    }
}