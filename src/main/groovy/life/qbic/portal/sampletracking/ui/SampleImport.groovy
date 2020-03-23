package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import life.qbic.portal.sampletracking.app.PortletController

class SampleImport extends VerticalLayout {
    private PortletController controller
    private SampleImportModel sampleImportModel

    private Button singleSampleAddButton
    private TextField additionalSampleId

    SampleImport(PortletController controller, SampleImportModel sampleImportModel) {
        super()
        this.controller = controller
        this.sampleImportModel = sampleImportModel
        initLayout()
    }

    private def initLayout() {

        FormLayout formLayout = new FormLayout()
        this.additionalSampleId = new TextField("Sample ID")
        this.additionalSampleId.setPlaceholder("QABCD004AO")
        this.singleSampleAddButton = new Button("Add Sample")

        formLayout.addComponents(this.additionalSampleId, this.singleSampleAddButton)
        // add components
        this.addComponents(formLayout)
    }

    private def registerListeners() {
        this.singleSampleAddButton.addClickListener({ event ->
            this.controller.querySampleById(this.additionalSampleId.getValue())
        })
    }
}
