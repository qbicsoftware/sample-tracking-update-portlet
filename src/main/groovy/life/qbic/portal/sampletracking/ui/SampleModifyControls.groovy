package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Label
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.app.PortletController

import com.vaadin.ui.Button
import com.vaadin.ui.DateField
import com.vaadin.ui.NativeSelect
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout

@Log4j2
class SampleModifyControls extends VerticalLayout{

    private PortletController controller
    private Label userEmailField
    private Button clearButton
    private Button updateSampleButton
    private NativeSelect<Location> locationSelectMenu
    private NativeSelect<Status> statusSelectMenu
    private DateField dateChooser


    private SampleModifyControls() {
        // disable default constructor
    }

    SampleModifyControls(PortletController portletController) {
        super()
        this.controller = portletController
        initLayout()
        registerListeners()
    }

    private def initLayout() {

        // Add textfield showing email address of portal user
        userEmailField = new Label()
        userEmailField.setValue("You are not logged in.")
        /* For Test purposes
        //TODO: remove in release
        userEmailField.setValue("CurrentDev@Qbic.com")
        */
        this.addComponent(userEmailField)

        // Add menu allowing location picking for new sample
        locationSelectMenu = new NativeSelect<>("Select Sample Location")

        /* For Test purposes
        //TODO remove in release
        locationSelectMenu.setItems("QBiC", "Home Office", "Laboratory", "Shipping")
        */
        this.addComponent(locationSelectMenu)


        // Add menu allowing date picking for new sample
        dateChooser = new DateField("Desired Arrival Date")
        dateChooser.setTextFieldEnabled(false)
        //TODO: choose date format to display
        this.addComponent(dateChooser)

        // Add menu allowing status selection for new sample
        statusSelectMenu = new NativeSelect<Status>("Desired Sample Status")
        statusSelectMenu.setEmptySelectionAllowed(false)
        statusSelectMenu.setItems(Status.values())
        /* For Test purposes
        //TODO: remove in release
        sampleSelectMenu.setItems("Waiting", "Processing", "Processed")
        */
        this.addComponent(statusSelectMenu)

        // Add button enabling sample update to SampleList
        updateSampleButton = new Button("Update Samples")
        this.addComponent(updateSampleButton)

        // Add clear Button to delete samples from SampleList
        clearButton = new Button("Clear List")
        this.addComponent(clearButton)
    }

    private void registerListeners() {
        this.clearButton.addClickListener({ event -> controller.clearSelection() })

    }
}
