package life.qbic.portal.sampletracking.web.views

import com.vaadin.ui.*
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.web.controllers.PortletController
import life.qbic.portal.sampletracking.web.ViewModel

@Log4j2
class ControlElements extends VerticalLayout {

    final static List<Status> FORBIDDEN_STATUS_OPTIONS = new ArrayList([Status.DATA_AT_QBIC, Status.METADATA_REGISTERED])

    final private PortletController controller
    final private ViewModel viewModel

    private Label userEmailField
    private Button clearButton
    private Button updateSampleButton
    private NativeSelect<Location> locationSelectMenu
    private NativeSelect<Status> statusSelectMenu
    private DateField dateChooser


    ControlElements(PortletController portletController, ViewModel viewModel) {
        super()
        this.controller = portletController
        this.viewModel = viewModel
        initLayout()
        registerListeners()
    }

    private def initLayout() {

        // Add text showing email address of portal user
        userEmailField = new Label()
        userEmailField.setValue("You are not logged in.")

        // Add menu allowing location picking for new sample
        locationSelectMenu = new NativeSelect<>("New Sample Location")
        locationSelectMenu.setItems(viewModel.availableLocations)

        // Add menu allowing date picking for new sample
        dateChooser = new DateField("New Arrival Date")
        dateChooser.setTextFieldEnabled(false)
        //TODO: choose date format to display

        // Add menu allowing status selection for new sample
        statusSelectMenu = new NativeSelect<Status>("New Sample Status")
        statusSelectMenu.setEmptySelectionAllowed(false)

        List<Status> selectableStatusOptions = Status.values().findAll { status ->
            !(status in FORBIDDEN_STATUS_OPTIONS)
        }

        statusSelectMenu.setItems(selectableStatusOptions)

        // Add button enabling sample update to SampleList
        updateSampleButton = new Button("Update Samples")

        // Add clear Button to delete samples from SampleList
        clearButton = new Button("Clear List")

        // Add all Vaadin components to layout
        HorizontalLayout row1 = new HorizontalLayout()
        row1.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT)
        HorizontalLayout row2 = new HorizontalLayout()
        row2.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT)
        HorizontalLayout row3 = new HorizontalLayout()
        row3.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT)

        row1.addComponentsAndExpand(userEmailField, dateChooser)
        row2.addComponentsAndExpand(locationSelectMenu, statusSelectMenu)
        row3.addComponentsAndExpand(updateSampleButton, clearButton)
        this.addComponents(row1, row2, row3)
    }

    private void registerListeners() {

        // Add listener to update button to upload Sample changes selected in view
        this.clearButton.addClickListener({ event -> this.viewModel.samples.clear() })

    }
}
