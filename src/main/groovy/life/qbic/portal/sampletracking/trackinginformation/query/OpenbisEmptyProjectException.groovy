package life.qbic.portal.sampletracking.trackinginformation.query

class OpenbisEmptyProjectException extends RuntimeException {

    OpenbisEmptyProjectException() {
        super()
    }

    OpenbisEmptyProjectException(String msg) {
        super(msg)
    }

    OpenbisEmptyProjectException(Throwable t) {
        super(t)
    }
}