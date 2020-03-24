package life.qbic.portal.sampletracking.ui

import com.google.gwt.event.logical.shared.ValueChangeEvent
import com.vaadin.server.Page
import com.vaadin.shared.Position
import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.Notification
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
        registerListeners()
    }

    private def initLayout() {

        FormLayout formLayout = new FormLayout()
        // Add textfield for sample Id input with placeholder sample id
        this.additionalSampleId = new TextField("Sample ID")
        this.additionalSampleId.setPlaceholder("QABCD004AO")
        // Add Button to add sample
        this.singleSampleAddButton = new Button("Add Sample")
        // Add components to layout
        formLayout.addComponents(this.additionalSampleId, this.singleSampleAddButton)

        this.addComponents(formLayout)
    }

    private def registerListeners() {
        // Add listener to add sample button
        this.singleSampleAddButton.addClickListener({ event ->
            // Get value from user input in textfield
            String sampleIdInput = this.additionalSampleId.getValue()
            try {
                this.controller.querySampleById(sampleIdInput)
                // if sample was found show success notification
                Notification notification = new Notification
                        (
                                "Success", "Sample Id: $sampleIdInput was added to Sample List",
                                Notification.Type.HUMANIZED_MESSAGE)
                notification.setDelayMsec(3000)
                notification.setPosition(Position.TOP_CENTER)
                notification.show(Page.getCurrent())
                //ToDo style notification e.g.
                // make notification background color for clearer distinction between
                // success and failed sample registration attempt or change Sample Id to be bold and italic
            }
            catch (Exception e) {
                // show notification if user input a sample id but it couldn't be found
                if (sampleIdInput) {
                    Notification notification = new Notification
                            (
                                    "Sample Id Error", "Sample Id: $sampleIdInput could not be found in database",
                                    Notification.Type.ERROR_MESSAGE)
                    notification.setDelayMsec(3000)
                    notification.setPosition(Position.TOP_CENTER)
                    notification.show(Page.getCurrent())

                }
                //show notification if no sample id was provided
                else {
                    Notification notification = new Notification
                            (
                                    "No Sample Id Input", "Please Input a Sample Id",
                                    Notification.Type.ERROR_MESSAGE)
                    notification.setDelayMsec(3000)
                    notification.setPosition(Position.TOP_CENTER)
                    notification.show(Page.getCurrent())

                }
            }
        })
    }
}
