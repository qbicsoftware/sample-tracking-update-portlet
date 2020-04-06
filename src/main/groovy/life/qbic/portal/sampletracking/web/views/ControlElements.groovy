package life.qbic.portal.sampletracking.web.views

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


    ControlElements(PortletController portletController, ViewModel viewModel) {
        super()
        this.controller = portletController
        this.viewModel = viewModel
        initLayout()
        registerListeners()
    }

    private def initLayout() {

        // Add textfield showing email address of portal user
        userEmailField = new Label()
        userEmailField.setValue("You are not logged in.")

        // Add menu allowing location picking for new sample
        locationSelectMenu = new NativeSelect<>("New Sample Location")
        locationSelectMenu.setItems(viewModel.availableLocations)
        /* For Test purposes
        //TODO remove in release
        locationSelectMenu.setItems("QBiC", "Home Office", "Laboratory", "Shipping")
        */

        // Add menu allowing date picking for new sample
        dateChooser = new DateField("New Arrival Date")
        dateChooser.setTextFieldEnabled(false)
        //TODO: choose date format to display

        // Add menu allowing status selection for new sample
        statusSelectMenu = new NativeSelect<Status>("New Sample Status")
        statusSelectMenu.setEmptySelectionAllowed(false)
        ArrayList<Status> filteredStatusList = new ArrayList<Status>([Status.DATA_AT_QBIC, Status.METADATA_REGISTERED])
        ArrayList<Status> selectableStatusList = getSelectableStatusList(filteredStatusList)
        System.out.println(selectableStatusList)
        statusSelectMenu.setItems(selectableStatusList)

        /* For Test purposes
        //TODO: remove in release
        sampleSelectMenu.setItems("Waiting", "Processing", "Processed")
        */

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

    private ArrayList<Status> getSelectableStatusList(ArrayList<Status> filterList) {
        ArrayList<Status> selectableStatusList = Status.values()
        for (item in filterList) {
            selectableStatusList.removeIf({ n -> (n.toString().toUpperCase() == item.toString().toUpperCase()) })
        }
        return selectableStatusList
    }

    private void registerListeners() {

        // Add listener to update button to upload Sample changes selected in view

        //ToDo Determine how Samples from Samplelist can be connected to user selected location, date and Status
     //   def selectedSampleIds = viewModel.requestSampleList()

        //ToDo Date and responsible Persons are stored in Location, but arrivalDate gets selected here, how is this resolved?
      //  this.updateSampleButton.addClickListener({ event -> controller.updateSamples(selectedSampleIds, locationSelectMenu.getValue(), statusSelectMenu.getValue()) })
        //Add listener to clear button to remove add samples to SampleList
        this.clearButton.addClickListener({ event -> this.viewModel.samples.clear() })

    }
}
