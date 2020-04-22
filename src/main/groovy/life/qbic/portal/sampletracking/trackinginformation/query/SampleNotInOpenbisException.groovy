package life.qbic.portal.sampletracking.trackinginformation.query

class SampleNotInOpenbisException extends RuntimeException {

    SampleNotInOpenbisException() {
        super()
    }

    SampleNotInOpenbisException(String msg) {
        super(msg)
    }

    SampleNotInOpenbisException(Throwable t) {
        super(t)
    }
}