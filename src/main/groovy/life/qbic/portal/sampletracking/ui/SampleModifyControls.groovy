package life.qbic.portal.sampletracking.ui

import groovy.util.logging.Log4j2
import life.qbic.portal.sampletracking.app.PortletController

import com.vaadin.ui.Button
import com.vaadin.ui.DateField
import com.vaadin.ui.NativeSelect
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout

import java.time.LocalDate

@Log4j2
class SampleModifyControls extends VerticalLayout{

    private PortletController controller
    private TextField userEmailField
    private Button clearButton
    private Button updateSampleButton
    private NativeSelect<String> locationSelectMenu
    private NativeSelect<String> sampleSelectMenu
    private DateField dateSelectMenu


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

        // Add textfield showing email address of responsible Person
        userEmailField = new TextField("Responsible Person Email")
        /* For Test purposes
        userEmailField.setValue("CurrentDev@Qbic.com") */
        userEmailField.setReadOnly(true)
        this.addComponent(userEmailField)

        // Add menu allowing location picking for new sample
        locationSelectMenu = new NativeSelect<>("Select Sample Location")
        /* For Test purposes
        locationSelectMenu.setItems("QBiC", "Home Office", "Laboratory", "Shipping") */
        this.addComponent(locationSelectMenu)

        // Add menu allowing date picking for new sample
        dateSelectMenu = new DateField("Select Sample Arrival Date")
        LocalDate localDate = LocalDate.now()
        dateSelectMenu.setValue(localDate)
        this.addComponent(dateSelectMenu)

        // Add menu allowing status selection for new sample
        sampleSelectMenu = new NativeSelect<>("Select Sample Status")
        /* For Test purposes
        sampleSelectMenu.setItems("Waiting", "Processing", "Processed") */
        this.addComponent(sampleSelectMenu)

        // Add button enabling sample update to SampleList
        updateSampleButton = new Button("Update Sample")
        this.addComponent(updateSampleButton)

        // Add clear Button to delete samples from SampleList
        clearButton = new Button("Clear Selected Samples")
        this.addComponent(clearButton)
    }

    private void registerListeners() {
        this.clearButton.addClickListener({ event -> controller.clearSelection() })

    }
}
