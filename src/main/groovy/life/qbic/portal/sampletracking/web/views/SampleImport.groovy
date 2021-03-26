package life.qbic.portal.sampletracking.web.views

import com.vaadin.ui.*
import groovy.util.logging.Log4j2
import life.qbic.portal.sampletracking.web.SampleParser
import life.qbic.portal.sampletracking.web.ViewModel
import life.qbic.portal.sampletracking.web.controllers.PortletController
import life.qbic.portal.sampletracking.web.views.samplefile.UploadComponent

@Log4j2
class SampleImport extends VerticalLayout {
    final private PortletController controller
    final private ViewModel viewModel

    private Button singleSampleAddButton
    private TextArea barcodeInputArea
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
        this.barcodeInputArea = new TextArea("Sample Codes")
        this.barcodeInputArea.setPlaceholder("Please enter a QBiC sample identifier. This can also be done in TSV format. The barcode has to be contained in the first column. Header rows are ignored.")
        // Add upload receiver to get uploaded file content

        uploadComponent = new UploadComponent()

        // Add Button to add sample
        this.singleSampleAddButton = new Button("Add Samples")
        // Add components to layout
        HorizontalLayout row1 = new HorizontalLayout()
        row1.setSizeFull()
        row1.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT)
        HorizontalLayout row2 = new HorizontalLayout()
        row2.setSizeFull()
        row2.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT)

        this.barcodeInputArea.setSizeFull()
        this.singleSampleAddButton.setSizeFull()
        row1.addComponents(new VerticalLayout(this.barcodeInputArea, this.singleSampleAddButton))


        this.uploadComponent.setWidth("33%")
        row2.addComponent(this.uploadComponent)

        this.addComponents(row1, row2)

        this.setComponentAlignment(row2, Alignment.MIDDLE_RIGHT)
    }

    private def registerListeners() {
        this.singleSampleAddButton.addClickListener({ event ->
            try {
                selectSampleFromTextfield()
            } catch (Exception e) {
                log.error("Unexpected Exception", e)
            }
        })

        this.uploadComponent.addUploadSucceededListener({ event ->
            try {
                selectSamplesFromFile(event.sampleIds)
            } catch (Exception e) {
                log.error("Unexpected Exception", e)
            }
        })
    }

    /**
     * Retrieves the value of the TextField. Sends a request to add the sample to the controller
     * @return
     */
    private def selectSampleFromTextfield() {
        // Get value from user input in textField
        String userInput = barcodeInputArea.getValue()
        List<String> sampleIds = SampleParser.extractSampleCodes(userInput)
        for (sampleId in sampleIds) {
            try {
                if (isSampleSelected(sampleId)) {
                    viewModel.failureNotifications.add("You already selected ${sampleId}")
                    log.warn("Tried to select ${sampleId} multiple times.")
                } else {
                    controller.selectSampleById(sampleId)
                }
            } catch (Exception e) {
                log.error("Unexpected error trying to add samples by id $sampleIds", e)
                viewModel.failureNotifications.add("Could not select new sample $sampleId." + e.getMessage())
            }
        }
        barcodeInputArea.clear()
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
