package life.qbic.portal.sampletracking.web.views


import com.vaadin.server.Page
import com.vaadin.ui.*
import groovy.util.logging.Log4j2
import life.qbic.portal.sampletracking.web.StyledNotification
import life.qbic.portal.sampletracking.web.controllers.PortletController
import life.qbic.portal.sampletracking.web.views.samplefile.UploadComponent

@Log4j2
class SampleImport extends VerticalLayout {
    final private PortletController controller

    private Button singleSampleAddButton
    private TextField additionalSampleId
    private UploadComponent uploadComponent

    SampleImport(PortletController controller) {
        super()
        this.controller = controller
        initLayout()
        registerListeners()
    }

    private def initLayout() {

        FormLayout formLayout = new FormLayout()
        // Add textfield for sample Id input with placeholder sample id
        this.additionalSampleId = new TextField("Sample ID")
        this.additionalSampleId.setPlaceholder("QABCD004AO")
        // Add upload receiver to get uploaded file content

        uploadComponent = new UploadComponent()

        // Add Button to add sample
        this.singleSampleAddButton = new Button("Add Sample")
        // Add components to layout
        formLayout.addComponents(this.additionalSampleId, this.singleSampleAddButton, this.uploadComponent)

        this.addComponents(formLayout)
    }

    private def registerListeners() {
        this.singleSampleAddButton.addClickListener({ event ->
            selectSampleFromTextfield()
        })

        this.uploadComponent.addUploadSucceededListener({ event ->
            controller.selectSamplesById(event.sampleIds)
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
