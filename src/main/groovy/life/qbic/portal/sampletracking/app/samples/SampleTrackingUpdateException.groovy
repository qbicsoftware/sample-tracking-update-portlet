package life.qbic.portal.sampletracking.app.samples

class SampleTrackingUpdateException extends RuntimeException{

    SampleTrackingUpdateException(){
        super()
    }

    SampleTrackingUpdateException(String msg) {
        super(msg)
    }

    SampleTrackingUpdateException(Throwable t){
        super(t)
    }
}
