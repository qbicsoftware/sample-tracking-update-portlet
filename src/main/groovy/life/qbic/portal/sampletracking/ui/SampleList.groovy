package life.qbic.portal.sampletracking.ui

import com.vaadin.data.provider.ListDataProvider
import com.vaadin.ui.Button
import com.vaadin.ui.Grid
import com.vaadin.ui.VerticalLayout
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.app.samples.selection.ModifySampleSelectionOutput

@Log4j2
class SampleList extends VerticalLayout implements ModifySampleSelectionOutput{
    private List<Sample> samples
    private Grid<Sample> sampleGrid
    private Button clearButton

    SampleList() {
        super()
        this.samples = new ArrayList<Sample>()

        //TODO: remove
        Sample testSample = new Sample()
        Location testLocation = new Location()
        testLocation.setName("Nowhere")
        testLocation.setStatus(Status.PROCESSING)
        testSample.setCode("ABCDE")
        testSample.setCurrentLocation(testLocation)

        this.samples.add(testSample)


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

        // Add clear Button to Layout
        clearButton = new Button("Clear samples")

        // Hand over generated gridlayout
        this.addComponents(this.sampleGrid, this.clearButton)
    }

    private void registerListeners() {
        this.clearButton.addClickListener({ event -> clearSamples() })
    }

    // Refresh sample display in grid
    void refreshView() {
        this.sampleGrid.getDataProvider().refreshAll()
    }
    // clear samples in grid
    void clearList() {
        this.samples.clear()
    }

    @Override
    void addSamples(Sample... samples) {
        this.samples.addAll(samples)
        refreshView()
    }

    @Override
    void removeSamples(Sample... samples) {
        this.samples.removeAll(samples)
        refreshView()
    }

    @Override
    void clearSamples() {
        this.clearList()
        refreshView()
    }
}
