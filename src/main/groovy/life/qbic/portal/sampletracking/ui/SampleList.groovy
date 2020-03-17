package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Grid
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
        sampleGrid = new Grid("Samples to update:")
        this.sampleGrid.setSelectionMode(Grid.SelectionMode.NONE);

        this.sampleGrid.setWidth("100%");
        this.sampleGrid.setHeight("30%");
        try {
            this.sampleGrid.addColumn({ sample -> sample.getCode() }).setCaption("Code")
            this.sampleGrid.addColumn({ sample -> sample.getCurrentLocation() }).setCaption("Current Location");
        } catch (Exception e) {
            log.error("Unexpected exception in building the sample Grid", e)
        }


        this.addComponent(this.sampleGrid)
    }

    void refreshList() {
        if(this.samples) {
            this.sampleGrid.setItems(this.samples)
        }
    }
}
