package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.GridLayout
import com.vaadin.ui.HorizontalLayout

class PortletView extends GridLayout {
    private PortletController controller

    PortletView(PortletController portletController) {
        super()
        this.controller = portletController
        initLayout()
    }

    private def initLayout() {
        SampleList sampleList = new SampleList()
        SampleModifyControls sampleControls= new SampleModifyControls(this.controller)
        SampleImport sampleImport = new SampleImport(this.controller)
        this.addComponents(sampleImport, sampleList, sampleControls)
    }
}
