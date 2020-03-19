package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.HorizontalLayout
import life.qbic.portal.sampletracking.app.PortletController

class PortletView extends HorizontalLayout {
    final private PortletController controller
    final private PortletViewModel portletViewModel

    private SampleList sampleList
    private SampleModifyControls sampleControls
    private SampleImport sampleImport

    PortletView(PortletController portletController, PortletViewModel portletViewModel,
                SampleList sampleList, SampleModifyControls sampleModifyControls, SampleImport sampleImport) {
        super()
        this.controller = portletController
        this.portletViewModel = portletViewModel
        this.sampleList = sampleList
        this.sampleControls = sampleModifyControls
        this.sampleImport = sampleImport
        initLayout()
    }

    private def initLayout() {
        /*
        //TODO remove
        Sample testSample = new Sample()
        Location testLocation = new Location()
        testLocation.setName("Nowhere")
        testLocation.setStatus(Status.PROCESSING)
        testSample.setCode("ABCDE")
        testSample.setCurrentLocation(testLocation)
        this.samples.add(testSample)*/

        this.addComponents(this.sampleImport, this.sampleList, this.sampleControls)
    }
}
