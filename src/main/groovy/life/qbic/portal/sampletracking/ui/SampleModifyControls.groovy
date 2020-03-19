package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.*
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.app.PortletController

@Log4j2
class SampleModifyControls extends VerticalLayout {


    final private PortletController controller
    final private SampleModifyControlsModel modifyControlsModel

    private Label userEmailField
    private Button clearButton
    private Button updateSampleButton
    private NativeSelect<Location> locationSelectMenu
    private NativeSelect<Status> statusSelectMenu
    private DateField dateChooser


    SampleModifyControls(PortletController portletController, SampleModifyControlsModel modifyControlsModel) {
        super()
        this.controller = portletController
        this.modifyControlsModel = modifyControlsModel
        initLayout()
        registerListeners()
    }

    private def initLayout() {

        // Add textfield showing email address of portal user
        userEmailField = new Label()
        userEmailField.setValue("You are not logged in.")

        // Add menu allowing location picking for new sample
        locationSelectMenu = new NativeSelect<>("Select Sample Location")
        /* For Test purposes
        //TODO remove in release
        locationSelectMenu.setItems("QBiC", "Home Office", "Laboratory", "Shipping")
        */

        // Add menu allowing date picking for new sample
        dateChooser = new DateField("Desired Arrival Date")
        dateChooser.setTextFieldEnabled(false)
        //TODO: choose date format to display

        // Add menu allowing status selection for new sample
        statusSelectMenu = new NativeSelect<Status>("Desired Sample Status")
        statusSelectMenu.setEmptySelectionAllowed(false)
        statusSelectMenu.setItems(Status.values())

        /* For Test purposes
        //TODO: remove in release
        sampleSelectMenu.setItems("Waiting", "Processing", "Processed")
        */

        // Add button enabling sample update to SampleList
        updateSampleButton = new Button("Update Samples")

        // Add clear Button to delete samples from SampleList
        clearButton = new Button("Clear List")

        // Add all Vaadin components to layout
        this.addComponents(userEmailField, locationSelectMenu, dateChooser, statusSelectMenu, updateSampleButton, clearButton)
    }

    private void registerListeners() {

        // Add listener to update button to upload Sample changes selected in view

        //ToDo Determine how Samples from Samplelist can be connected to user selected location, date and Status
        def selectedSampleIdArray = ViewModel.requestSampleList().collect { it.getCode }

        //ToDo Date and responsible Persons are stored in Location, but arrivalDate gets selected here, how is this resolved?
        this.updateSampleButton.addClickListener({ event -> controller.updateSamples(selectedSampleIdArray, locationSelectMenu.getValue(), statusSelectMenu.getValue()) })
        //Add listener to clear button to remove add samples to SampleList
        this.clearButton.addClickListener({ event -> controller.clearSelection() })

    }
}
