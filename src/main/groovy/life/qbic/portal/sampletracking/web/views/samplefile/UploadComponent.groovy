package life.qbic.portal.sampletracking.web.views.samplefile

import com.vaadin.ui.Upload
import com.vaadin.ui.VerticalLayout
import groovy.util.logging.Log4j2
import life.qbic.datamodel.identifiers.SampleCodeFunctions
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
    public final static String MIME_TYPE = "text/csv"

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
        upload.setAcceptMimeTypes(MIME_TYPE)
        upload.setButtonCaption("Select From File")
        upload.setReceiver(this.receiver)
        upload.addSucceededListener(this.succeededListener)
        upload.addFailedListener(this.failedListener)
        this.upload = upload

    }

    private def initLayout() {
        this.setSpacing(false)
        this.setMargin(false)
        this.upload.setSizeFull()
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
                String separator = ","
                try {
                    Set inputLines = new String(uploadContent.toByteArray()).split("\n") as Set
                    for (line in inputLines) {
                        String sampleCode = line.split(separator)[0]
                        if (SampleCodeFunctions.isQbicBarcode(sampleCode)) {
                            sampleIds.add(sampleCode)
                        } else {
                            String invalidCodeMessage = "Entry '${sampleCode}' is not a valid QBiC Sample Code."
                            log.error(invalidCodeMessage)
                        }
                    }
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
