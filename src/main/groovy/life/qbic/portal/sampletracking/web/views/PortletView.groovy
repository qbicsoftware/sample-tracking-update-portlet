package life.qbic.portal.sampletracking.web.views

import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.VerticalLayout
import life.qbic.portal.sampletracking.web.ViewModel
import life.qbic.portal.sampletracking.web.controllers.PortletController

class PortletView extends VerticalLayout {
    final private PortletController controller
    final private ViewModel portletViewModel

    private SampleList sampleList
    private ControlElements sampleControls
    private SampleImport sampleImport

    PortletView(PortletController portletController, ViewModel portletViewModel,
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
        this.setMargin(false)
        this.setSpacing(false)
        this.addComponentsAndExpand(this.sampleImport, this.sampleList, this.sampleControls)
        this.setSizeFull()
    }
}
