package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.GridLayout
import com.vaadin.ui.Upload.Receiver
import life.qbic.portal.sampletracking.app.PortletController

class PortletView extends GridLayout {
    private PortletController controller
    private Receiver receiver

    PortletView(PortletController portletController, Receiver receiver) {
        super()
        this.controller = portletController
        this.receiver = receiver
        initLayout()
    }

    private def initLayout() {
        SampleList sampleList = new SampleList()
        SampleModifyControls sampleControls= new SampleModifyControls(this.controller)
        SampleImport sampleImport = new SampleImport(this.controller, this.receiver)
        this.addComponents(sampleImport, sampleList, sampleControls)
    }
}
