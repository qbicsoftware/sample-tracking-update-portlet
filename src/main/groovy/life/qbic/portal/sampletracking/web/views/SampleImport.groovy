package life.qbic.portal.sampletracking.web.views

import com.vaadin.server.Page

import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.TextField
import com.vaadin.ui.Upload
import com.vaadin.ui.Upload.Receiver
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Notification
import groovy.util.logging.Log4j2
import life.qbic.portal.components.Uploader
import life.qbic.portal.sampletracking.web.controllers.PortletController
import life.qbic.portal.sampletracking.web.StyledNotification

@Log4j2
class SampleImport extends VerticalLayout {
    final private PortletController controller
    final private Receiver uploadReceiver

    private Button singleSampleAddButton
    private TextField additionalSampleId
    private Upload fileSampleAddUpload

    SampleImport(PortletController controller, Receiver uploadReceiver) {
        super()
        this.controller = controller
        this.uploadReceiver = uploadReceiver
        initLayout()
        registerListeners()
    }

    private def initLayout() {

        FormLayout formLayout = new FormLayout()
        // Add textfield for sample Id input with placeholder sample id
        this.additionalSampleId = new TextField("Sample ID")
        this.additionalSampleId.setPlaceholder("QABCD004AO")
        // Add upload receiver to get uploaded file content
        Uploader receiver = new Uploader()
        // Add upload field for sample upload via file
        this.fileSampleAddUpload = new Upload("Upload File here", receiver)
        //Start upload only after add samples button was pressed
        this.fileSampleAddUpload.setImmediateMode(false)
        this.fileSampleAddUpload.setButtonCaption("Add File IDs")

        //TODO: Restrict Fileupload to specific Type and maximum size,
        // add possibility of progressbar?

        // Add Button to add sample
        this.singleSampleAddButton = new Button("Add Sample")
        // Add components to layout
        formLayout.addComponents(this.additionalSampleId, this.singleSampleAddButton, this.fileSampleAddUpload)

        this.addComponents(formLayout)
    }

    private def registerListeners() {
        this.singleSampleAddButton.addClickListener({ event ->
            selectSampleFromTextfield()
        })

        // display notification that file upload was successful and samples were added to grid
        this.fileSampleAddUpload.addSucceededListener({ event ->

            Upload.Receiver receiver = this.fileSampleAddUpload.getReceiver()
            FileOutputStream uploadedFileStream = receiver.receiveUpload("temp", ".csv")


            //ToDo check if uploadedfileStream is empty
            // to avoid success notification to user when no file was selected

            if (uploadedFileStream) {

                //ToDo implement controller access of uploaded file here,
                // should an outputStream be provided as File or should the samples be extracted here into List of Samples?

                // display notification if file upload and addition to sample list was successful
               StyledNotification uploadFileSuccessNotification = new StyledNotification("Upload successful, File could be uploaded successfully")
                uploadFileSuccessNotification.show(Page.getCurrent())
            }
            // display notification if user has not selected a file to upload
            else {
                StyledNotification noFileProvidedNotification = new StyledNotification("No File selected", "Please specify a file to upload", Notification.Type.WARNING_MESSAGE)
                noFileProvidedNotification.show(Page.getCurrent())

            }
        })

        // display notification if file upload has failed
        this.fileSampleAddUpload.addFailedListener({ event ->
            StyledNotification uploadFileFailedNotification = new StyledNotification("Upload failed", "File upload failed", Notification.Type.ERROR_MESSAGE)
            uploadFileFailedNotification.show(Page.getCurrent())
        })
    }

    /**
     * Retrieves the value of the TextField. Sends a request to add the sample to the controller
     * @return
     */
    private def selectSampleFromTextfield() {
        // Get value from user input in textField
        String sampleIdInput = additionalSampleId.getValue()
        try {
            if (sampleIdInput?.trim()) {

                controller.selectSampleById(sampleIdInput)
                // if sample was found show success notification
                StyledNotification uploadIdSuccessNotification = new StyledNotification("Success", "Added $sampleIdInput")
                uploadIdSuccessNotification.show(Page.getCurrent())

            } else {
                StyledNotification emptyId = new StyledNotification("Empty ID", "Please enter a sample ID and try again.", Notification.Type.ASSISTIVE_NOTIFICATION)
                emptyId.show(Page.getCurrent())

            }
        } catch (Exception e) {
            log.error("Unexpected error trying to add sample by id $sampleIdInput", e)
            StyledNotification couldNotSelectSampleNotification = new StyledNotification("Could not select sample $sampleIdInput", Notification.Type.ERROR_MESSAGE)
            couldNotSelectSampleNotification.show(Page.getCurrent())
        }
    }
}
