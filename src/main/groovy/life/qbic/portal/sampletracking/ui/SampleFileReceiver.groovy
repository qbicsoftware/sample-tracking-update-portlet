package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Upload
import groovy.util.logging.Log4j2

@Log4j2
class SampleFileReceiver implements Upload.Receiver, Upload.SucceededListener {
    @Override
    OutputStream receiveUpload(String filename, String mimeType) {
        return null
    }

    @Override
    void uploadSucceeded(Upload.SucceededEvent event) {

    }
}
