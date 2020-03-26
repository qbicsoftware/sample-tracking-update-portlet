package life.qbic.portal.sampletracking.ui

import com.vaadin.server.Page

import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.TextField
import com.vaadin.ui.Upload
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Notification

import life.qbic.portal.components.Uploader
import life.qbic.portal.sampletracking.app.PortletController

class SampleImport extends VerticalLayout {
    private PortletController controller
    private SampleImportModel sampleImportModel

    private Button singleSampleAddButton
    private TextField additionalSampleId
    private Upload fileSampleAddUpload

    SampleImport(PortletController controller, SampleImportModel sampleImportModel) {
        super()
        this.controller = controller
        this.sampleImportModel = sampleImportModel
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
            // Get value from user input in textField
            String sampleIdInput = this.additionalSampleId.getValue()
            try {
                this.controller.selectSampleFromId(sampleIdInput)
                // if sample was found show success notification
                StyledNotification uploadIdSuccessNotification = new StyledNotification("Success", "Added $sampleIdInput")
                uploadIdSuccessNotification.show(Page.getCurrent())

                //ToDo style notification e.g.
                // make notification background color for clearer distinction between
                // success and failed sample registration attempt or change Sample Id to be bold and italic

            }
            catch (Exception e) {
                // show notification if user input a sample id but it couldn't be found
                if (sampleIdInput) {
                    StyledNotification noSampleIdFoundNotification = new StyledNotification("Sample Id Error", "Sample ID: $sampleIdInput could not be found in database", Notification.Type.ERROR_MESSAGE)
                    noSampleIdFoundNotification.show(Page.getCurrent())
                }
                //show notification if no sample id was provided
                else {
                    StyledNotification noSampleIdProvidedNotification = new StyledNotification("No Sample Id Input", "Please Input a Sample Id", Notification.Type.WARNING_MESSAGE)
                    noSampleIdProvidedNotification.show(Page.getCurrent())
                }

            }
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
}
