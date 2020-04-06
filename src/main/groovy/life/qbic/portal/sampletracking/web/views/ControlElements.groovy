package life.qbic.portal.sampletracking.web.views

import com.vaadin.data.provider.ListDataProvider
import com.vaadin.ui.*
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.web.controllers.PortletController
import life.qbic.portal.sampletracking.web.ViewModel

@Log4j2
class ControlElements extends VerticalLayout {


    final private PortletController controller
    final private ViewModel viewModel

    private Label userEmailField
    private Button clearButton
    private Button updateSampleButton
    private NativeSelect<Location> locationSelectMenu
    private NativeSelect<Status> statusSelectMenu
    private DateField dateChooser
    private String userEmail

    private ControlElements() {
        //prevent default constructor initialization
        throw new AssertionError("${ControlElements.getSimpleName()} cannot be initialized by its default constructor.")
    }
    ControlElements(PortletController portletController, ViewModel viewModel, String userEmail) {
        super()
        this.controller = portletController
        this.viewModel = viewModel
        this.userEmail = userEmail
        initLayout()
        registerListeners()
        portletController.queryAllLocationsForPerson(userEmail)
    }

    private def initLayout() {

        // Add textfield showing email address of portal user
        userEmailField = new Label()
        userEmailField.setValue(userEmail)

        // Add menu allowing location picking for new sample
        locationSelectMenu = new NativeSelect<>()
        locationSelectMenu.setCaption("Available locations")
        locationSelectMenu.emptySelectionAllowed = false
        locationSelectMenu.setDataProvider(new ListDataProvider<>(viewModel.availableLocations))
        locationSelectMenu.setItemCaptionGenerator({it -> it?.name ?: "unknown"})

        // Add menu allowing date picking for new sample
        dateChooser = new DateField("Desired Arrival Date")
        dateChooser.setTextFieldEnabled(false)
        //TODO: choose date format to display

        // Add menu allowing status selection for new sample
        statusSelectMenu = new NativeSelect<Status>("Desired Sample Status")
        statusSelectMenu.setEmptySelectionAllowed(false)
        statusSelectMenu.setItems(Status.values())


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
     //   def selectedSampleIds = viewModel.requestSampleList()
        this.updateSampleButton.addClickListener({event ->
                this.controller.updateSamples(
                        viewModel.samples.asList(),
                        locationSelectMenu.getValue(),
                        statusSelectMenu.getValue(),
                        dateChooser.getValue()
                )

        })

        //ToDo Date and responsible Persons are stored in Location, but arrivalDate gets selected here, how is this resolved?
      //  this.updateSampleButton.addClickListener({ event -> controller.updateSamples(selectedSampleIds, locationSelectMenu.getValue(), statusSelectMenu.getValue()) })
        //Add listener to clear button to remove add samples to SampleList
        this.clearButton.addClickListener({ event -> this.viewModel.samples.clear() })

    }
}
