package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Button
import com.vaadin.ui.Grid
import com.vaadin.ui.Label
import com.vaadin.ui.Notification
import com.vaadin.ui.VerticalLayout
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample

@Log4j2
class SampleList extends VerticalLayout{
    private List<Sample> samples
    private Grid<Sample> sampleGrid

    SampleList() {
        super()
        initLayout()
        refreshList()
    }

    private def initLayout() {

        //Define Layout and Label
        VerticalLayout gridLayout = new VerticalLayout()
        Label gridLabel = new Label("Samples to update: ")

        //Set Grid properties
        this.sampleGrid.setSelectionMode(Grid.SelectionMode.NONE);

        this.sampleGrid.setWidth("100%");
        this.sampleGrid.setHeight("30%");

        //Add data to grid and specify which items should be shown in grid
        try {
            sampleGrid.setItems(samples); ;
            this.sampleGrid.addColumn({ sample -> sample.getCode() }).setCaption("Id")
            this.sampleGrid.addColumn({ sample -> sample.getCurrentLocation() }).setCaption("Current Location");
            this.sampleGrid.addColumn({sample -> sample.getCurrentLocation().getStatus()}).setCaption("Status")
        } catch (Exception e) {
            log.error("Unexpected exception in building the sample Grid", e)
        }

        // Add clear Button to Layout
        Button clearButton = new Button("Clear samples")

        // Add clicklistener to clear list upon button activation
        clearButton.addClickListener({ event ->
            clearList();
            Notification.show("Samples removed from list",
                    Notification.Type.HUMANIZED_MESSAGE)
        })

        // Add all components to gridlayout
        gridLayout.addComponent(gridLabel, this.sampleGrid, clearButton);

        // Hand over generated gridlayout
        this.addComponent(gridLayout)
    }

    // Refresh samples in grid
    void refreshList() {
        if(this.samples) {
            this.sampleGrid.setItems(this.samples)
        } else {
            log.warn("There are no samples to be displayed. Clearing sample grid.")
            clearList();
        }
    }
    // clear samples in grid
    void clearList() {
        this.sampleGrid.setItems(new ArrayList<Sample>())
    }
}
