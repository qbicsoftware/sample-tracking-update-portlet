package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.VerticalLayout
import life.qbic.portal.sampletracking.app.PortletController

class SampleModifyControls extends VerticalLayout{

    private PortletController controller

    SampleModifyControls(PortletController portletController) {
        super()
        this.controller = portletController
        initLayout()
    }

    private def initLayout() {

    }
}
