package life.qbic.portal.sampletracking.web.views


import com.vaadin.data.provider.ListDataProvider
import com.vaadin.server.Sizeable
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
class ControlElements extends GridLayout {

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
        super(2, 4)
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
        List<Status> selectableStatusOptions = SAMPLE_STATUSES
        statusSelectMenu.setValue(SAMPLE_STATUSES.get(0))

        statusSelectMenu.setItems(selectableStatusOptions)

        // Add button enabling sample update to SampleList
        updateSampleButton = new Button("Update Samples")

        // Add clear Button to delete samples from SampleList
        clearButton = new Button("Clear List")

        this.setSizeFull()
        this.setMargin(true)
        this.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
        this.setHideEmptyRowsAndColumns(false)
        this.setSpacing(true)

        this.addComponent(dateChooser, 0, 0, 0, 0)
        this.addComponent(userEmailField, 0, 1, 0, 1)
        this.addComponent(clearButton, 0, 3, 0, 3)

        this.addComponent(locationSelectMenu , 1, 0, 1, 0)
        this.addComponent(statusSelectMenu, 1, 1, 1, 1)
        this.addComponent(updateSampleButton, 1, 3, 1, 3)


        Iterator<Component> childrenIterator = this.iterator()

        while(childrenIterator.hasNext()) {
            Component component = childrenIterator.next()
            if (component instanceof Sizeable) {
                component.setWidth("100%")
            }
        }

        // set alignment for right column
        for (int rowId = 0; rowId < this.getRows(); rowId++)  {
            Component component = this.getComponent(1, rowId)
            if (component) {
                this.setComponentAlignment(this.getComponent(1, rowId), Alignment.MIDDLE_RIGHT)
            }
        }

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
