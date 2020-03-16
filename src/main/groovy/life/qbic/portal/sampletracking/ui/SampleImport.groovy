package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.TextField
import com.vaadin.ui.Upload
import com.vaadin.ui.Upload.Receiver
import com.vaadin.ui.VerticalLayout
import life.qbic.portal.sampletracking.app.PortletController

class SampleImport extends VerticalLayout{
    private PortletController controller
    private Receiver receiver

    private Button singleSampleAddButton
    private TextField additionalSampleId

    SampleImport(PortletController controller, Receiver receiver) {
        super()
        this.controller = controller
        this.receiver = receiver
        initLayout()
    }

    private def initLayout() {
        Upload sampleFileUpload = new Upload("Upload Multiple Ids", this.receiver)


        FormLayout formLayout = new FormLayout()
        additionalSampleId = new TextField("Sample ID")
        this.additionalSampleId.setPlaceholder("QABCD004AO");
        this.singleSampleAddButton = new Button("Add Sample")

        formLayout.addComponents(this.additionalSampleId, this.singleSampleAddButton)
        // add components
        this.addComponents(formLayout)
    }

    private def registerListeners() {
        this.singleSampleAddButton.addClickListener({ event ->
            this.controller.selectSingleSample(this.additionalSampleId.getValue())
        })
    }
}
