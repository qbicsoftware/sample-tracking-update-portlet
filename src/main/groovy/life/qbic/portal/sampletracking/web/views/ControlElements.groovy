package life.qbic.portal.sampletracking.web.views


import com.vaadin.data.provider.ListDataProvider
import com.vaadin.shared.ui.datefield.DateTimeResolution
import com.vaadin.ui.*
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.web.DateConverter
import life.qbic.portal.sampletracking.web.ViewModel
import life.qbic.portal.sampletracking.web.controllers.PortletController

import java.time.LocalDateTime

@Log4j2
class ControlElements extends VerticalLayout {

    final static List<Status> SAMPLE_STATUSES = [Status.SAMPLE_QC_PASS, Status.SAMPLE_QC_FAIL, Status.SEQUENCING, Status.SEQUENCING_COMPLETE]
    final private PortletController controller
    final private ViewModel viewModel

    private Label userEmailField
    private Button clearButton
    private Button updateSampleButton
    private NativeSelect<Location> locationSelectMenu
    private NativeSelect<Status> statusSelectMenu
    private DateTimeField dateChooser
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
    }

    private def initLayout() {

        // Add text showing email address of portal user
        userEmailField = new Label()
        userEmailField.setValue(userEmail)

        // Add menu allowing location picking for new sample
        locationSelectMenu = new NativeSelect<>()
        locationSelectMenu.setCaption("New Sample Location")
        locationSelectMenu.emptySelectionAllowed = false
        locationSelectMenu.setDataProvider(new ListDataProvider<>(viewModel.availableLocations))
        locationSelectMenu.setItemCaptionGenerator({ it -> it?.name ?: "unknown" })

        // Add menu allowing date picking for new sample
        dateChooser = new DateTimeField("New Arrival Date [$DateConverter.SYSTEM_ZONE_ID]")
        dateChooser.setZoneId(DateConverter.SYSTEM_ZONE_ID)
        dateChooser.setTextFieldEnabled(true)
        dateChooser.setDateFormat(DateConverter.DISPLAY_PATTERN)
        dateChooser.setResolution(DateTimeResolution.MINUTE)
        dateChooser.setValue(LocalDateTime.now(DateConverter.SYSTEM_ZONE_ID))

        // Add menu allowing status selection for new sample
        statusSelectMenu = new NativeSelect<Status>("New Sample Status")
        statusSelectMenu.setEmptySelectionAllowed(false)
        // Set a default value for the status
        statusSelectMenu.setValue(Status.WAITING)
        List<Status> selectableStatusOptions = SAMPLE_STATUSES

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
        this.updateSampleButton.addClickListener({event ->
                this.controller.updateSamples(
                        viewModel.samples.toList().collect { it.code },
                        locationSelectMenu.getValue(),
                        statusSelectMenu.getValue(),
                        dateChooser.getValue()
                )

        })

        this.locationSelectMenu.addAttachListener({event ->
            this.locationSelectMenu.selectedItem = this.viewModel.availableLocations?.get(0) as Location
        }
        )

        this.clearButton.addClickListener({ event -> this.viewModel.samples.clear() })

    }
}
