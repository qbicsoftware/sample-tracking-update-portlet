package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.VerticalLayout

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
