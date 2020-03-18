package life.qbic.portal.sampletracking.ui

import com.vaadin.data.provider.ListDataProvider
import com.vaadin.ui.Button
import com.vaadin.ui.Grid
import com.vaadin.ui.VerticalLayout
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status

@Log4j2
class SampleList extends VerticalLayout {
    private List<Sample> samples
    private Grid<Sample> sampleGrid

    private SampleList() {
        // disable default constructor
    }

    SampleList(List<Sample> samples) {
        super()
        this.samples = samples
        initLayout()
        registerListeners()
    }

    private def initLayout() {

        this.sampleGrid = new Grid<Sample>("Samples to update:", new ListDataProvider<Sample>(this.samples))

        //Set Grid properties
        this.sampleGrid.setSelectionMode(Grid.SelectionMode.NONE);

        this.sampleGrid.setWidth("100%");
        this.sampleGrid.setHeight("30%");


        try {
            this.sampleGrid.addColumn({ sample -> sample.getCode() }).setCaption("Sample Code")
            this.sampleGrid.addColumn({ sample -> sample.getCurrentLocation().getName() }).setCaption("Current Location");
            this.sampleGrid.addColumn({sample -> sample.getCurrentLocation().getStatus()}).setCaption("Status")
        } catch (Exception e) {
            log.error("Unexpected exception in building the sample Grid", e)
        }

        // Hand over generated gridlayout
        this.addComponent(this.sampleGrid)
    }

    private void registerListeners() {

    }

    // Refresh sample display in grid
    void refreshView() {
        this.sampleGrid.getDataProvider().refreshAll()
    }
}
