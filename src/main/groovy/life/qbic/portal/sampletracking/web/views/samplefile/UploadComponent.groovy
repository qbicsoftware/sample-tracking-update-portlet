package life.qbic.portal.sampletracking.web.views.samplefile


import com.vaadin.ui.Upload
import com.vaadin.ui.VerticalLayout
import groovy.util.logging.Log4j2

import static com.vaadin.ui.Upload.*


/**
 * Contains an com.vaadin.ui.Upload element and implements its functionality
 * @param <T>
 */
@Log4j2
class UploadComponent extends VerticalLayout {
    private Receiver receiver
    private SucceededListener succeededListener
    private FailedListener failedListener
    private Upload upload
    private ByteArrayOutputStream uploadContent
    public static String mimeType = "text/csv"

    private List<UploadSucceededListener> uploadSucceededListenerList

    UploadComponent() {
        this.uploadSucceededListenerList = new ArrayList<>()
        setupComponent()
        initLayout()
    }

    private def setupComponent() {
        this.receiver = setupReceiver()
        this.succeededListener = setupSucceededListener()
        this.failedListener = setupFailedListener()


        Upload upload = new Upload()
        // start upload after file was selected
        upload.setImmediateMode(true)
        upload.setAcceptMimeTypes(mimeType)
        upload.setButtonCaption("Upload CSV File")
        upload.setReceiver(this.receiver)
        upload.addSucceededListener(this.succeededListener)
        upload.addFailedListener(this.failedListener)
        this.upload = upload

    }

    private def initLayout() {
        this.addComponents(this.upload)
    }


    private Receiver setupReceiver() {
        return new Receiver() {
            @Override
            OutputStream receiveUpload(String filename, String mimeType) {
                try {
                    uploadContent = new ByteArrayOutputStream()
                    return uploadContent
                } catch (Exception e) {
                    log.error("Unexpected. Could not upload to temporary file ${tempFile.getAbsolutePath()}.", e.getMessage())
                    throw e
                }

            }
        }
    }

    private SucceededListener setupSucceededListener() {
        return new SucceededListener() {
            @Override
            void uploadSucceeded(SucceededEvent event) {

                Set<String> sampleIds = [] as Set

                try {
                    sampleIds = new String(uploadContent.toByteArray()).split("\n") as Set
                    fireUploadSuccessEvent(sampleIds)
                } catch (Exception e) {
                    log.error ("The parsing of the sample ids from the output stream failed", e)
                } finally {
                    closeStream()
                }
            }
        }
    }

    private void closeStream(){
        uploadContent.flush()
        uploadContent.close()
    }

    private FailedListener setupFailedListener() {
        return new FailedListener() {
            @Override
            void uploadFailed(FailedEvent event) {
                log.error("Upload failed.", event.reason)
                closeStream()
            }
        }
    }

    private def fireUploadSuccessEvent(Set<String> sampleIds) {
        def successEvent =  new UploadSucceededEvent(sampleIds)
        for (uploadSucceededListener in uploadSucceededListenerList) {
            uploadSucceededListener.uploadSucceeded(successEvent)
        }
    }

    def addUploadSucceededListener(UploadSucceededListener uploadSucceededListener) {
        this.uploadSucceededListenerList.add(uploadSucceededListener)
    }
    def removeUploadSucceededListener(UploadSucceededListener uploadSucceededListener) {
        this.uploadSucceededListenerList.remove(uploadSucceededListener)
    }

}
