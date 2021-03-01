package life.qbic.portal.sampletracking.web.views

import com.vaadin.ui.*
import groovy.util.logging.Log4j2
import life.qbic.portal.sampletracking.web.ViewModel
import life.qbic.portal.sampletracking.web.controllers.PortletController
import life.qbic.portal.sampletracking.web.views.samplefile.UploadComponent

@Log4j2
class SampleImport extends VerticalLayout {
    final private PortletController controller
    final private ViewModel viewModel

    private Button singleSampleAddButton
    private TextField additionalSampleId
    private UploadComponent uploadComponent

    SampleImport(PortletController controller, ViewModel viewModel) {
        super()
        this.controller = controller
        this.viewModel = viewModel
        initLayout()
        registerListeners()
    }

    private def initLayout() {
        // Add textfield for sample Id input with placeholder sample id
        this.additionalSampleId = new TextField("Sample ID")
        this.additionalSampleId.setPlaceholder("Please enter a QBiC sample identifier")
        // Add upload receiver to get uploaded file content

        uploadComponent = new UploadComponent()

        // Add Button to add sample
        this.singleSampleAddButton = new Button("Add Sample")
        // Add components to layout
        HorizontalLayout row1 = new HorizontalLayout()
        row1.setSizeFull()
        row1.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT)
        HorizontalLayout row2 = new HorizontalLayout()
        row2.setSizeFull()
        row2.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT)

        this.additionalSampleId.setSizeFull()
        this.singleSampleAddButton.setSizeFull()
        row1.addComponents(this.additionalSampleId, this.singleSampleAddButton)
        row1.setExpandRatio(additionalSampleId, 2)
        row1.setExpandRatio(singleSampleAddButton, 1)

        this.uploadComponent.setWidth("33%")
        row2.addComponent(this.uploadComponent)

        this.addComponents(row1, row2)

        this.setComponentAlignment(row2, Alignment.MIDDLE_RIGHT)
    }

    private def registerListeners() {
        this.singleSampleAddButton.addClickListener({ event ->
            selectSampleFromTextfield()
        })

        this.uploadComponent.addUploadSucceededListener({ event ->
            selectSamplesFromFile(event.sampleIds)
        })
    }

    /**
     * Retrieves the value of the TextField. Sends a request to add the sample to the controller
     * @return
     */
    private def selectSampleFromTextfield() {
        // Get value from user input in textField
        String sampleId = additionalSampleId.getValue()
        try {
            if (sampleId?.trim()) {
                if (isSampleSelected(sampleId)) {
                    viewModel.failureNotifications.add("You already selected ${sampleId}")
                    log.warn("Tried to select ${sampleId} multiple times.")
                } else {
                    controller.selectSampleById(sampleId)
                }
            } else {
                viewModel.failureNotifications.add("Please enter a sample ID and try again.")
            }
        } catch (Exception e) {
            log.error("Unexpected error trying to add sample by id $sampleId", e)
            viewModel.failureNotifications.add("Could not select sample $sampleId")
        }
    }

    private selectSamplesFromFile(List<String> sampleIds) {
        List<String> unselectedIds = sampleIds.findAll { sampleId -> ! isSampleSelected(sampleId) }.unique()
        if (unselectedIds.size() < 1) {
            log.debug("No new sample codes in list.")
            viewModel.failureNotifications.add("There are no new sample codes in the selected file.")
        } else {
            try {
                controller.selectSamplesById(unselectedIds)
            } catch (Exception e) {
                log.error("Unexpected error trying to add samples by id $unselectedIds", e)
                viewModel.failureNotifications.add("Could not select new samples from file")
            }
        }
    }

    private boolean isSampleSelected(String sampleId) {
        return viewModel.samples.any { it.code  == sampleId }
    }
}
