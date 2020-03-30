package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.HorizontalLayout
import life.qbic.portal.sampletracking.app.PortletController

class PortletView extends HorizontalLayout {
    final private PortletController controller
    final private PortletViewModel portletViewModel

    private SampleList sampleList
    private ControlElements sampleControls
    private SampleImport sampleImport

    PortletView(PortletController portletController, PortletViewModel portletViewModel,
                SampleList sampleList, ControlElements sampleModifyControls, SampleImport sampleImport) {
        super()
        this.controller = portletController
        this.portletViewModel = portletViewModel
        this.sampleList = sampleList
        this.sampleControls = sampleModifyControls
        this.sampleImport = sampleImport
        initLayout()
    }

    private def initLayout() {
        this.addComponents(this.sampleImport, this.sampleList, this.sampleControls)
    }
}
