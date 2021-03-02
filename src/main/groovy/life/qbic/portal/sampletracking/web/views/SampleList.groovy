package life.qbic.portal.sampletracking.web.views

import com.vaadin.data.provider.ListDataProvider
import com.vaadin.shared.ui.grid.HeightMode
import com.vaadin.ui.Grid
import com.vaadin.ui.VerticalLayout
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.web.DateConverter
import life.qbic.portal.sampletracking.web.ViewModel

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

@Log4j2
class SampleList extends VerticalLayout {

    public ViewModel viewModel
    public Grid<Sample> sampleGrid

    // Suppress default constructor. Instantiation without parameters not desired.
    private SampleList() {
        // prevents accidental call from inside the class
        throw new AssertionError()
    }

    SampleList(ViewModel viewModel) {
        super()
        this.viewModel = Objects.requireNonNull(viewModel, "View model must not be null")
        initLayout()
        setupDataProvider()
        registerListeners()
    }

    private def initLayout() {

        this.sampleGrid = new Grid<Sample>("Samples to update:")

        //Set Grid properties
        this.sampleGrid.setSelectionMode(Grid.SelectionMode.NONE)

        this.sampleGrid.setWidth("100%")
        this.sampleGrid.setHeightMode(HeightMode.UNDEFINED)


        try {
            this.sampleGrid.addColumn({ sample -> sample.getCode() }).setCaption("Sample Code")
            this.sampleGrid.addColumn({ sample -> sample.getCurrentLocation().getName() }).setCaption("Current Location")
            this.sampleGrid.addColumn({ sample -> sample.getCurrentLocation().getStatus() }).setCaption("Status")
            this.sampleGrid.addColumn({ sample ->
                String locationDate = sample.getCurrentLocation().getArrivalDate()
                return DateConverter.format(locationDate)
            }).setCaption("Arrival Date [$DateConverter.SYSTEM_ZONE_ID]")
        } catch (Exception e) {
            log.error("Unexpected exception in building the sample Grid", e)
        }

        // Hand over generated gridlayout
        this.addComponent(this.sampleGrid)
    }


    private void setupDataProvider() {
        this.sampleGrid.setDataProvider(new ListDataProvider<Sample>(this.viewModel.samples))
    }

    private void registerListeners() {
        // just to make sure the grid displays valid information at all times
        this.viewModel.samples.addPropertyChangeListener( new PropertyChangeListener() {
            @Override
            void propertyChange(PropertyChangeEvent evt) {
                refreshView()
            }
        })
    }


    void refreshView() {
        this.sampleGrid.getDataProvider().refreshAll()
    }
}
