package life.qbic.portal.sampletracking

import groovy.util.logging.Log4j2
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.ui.PortletView
import life.qbic.portal.sampletracking.ui.SampleFileReceiver
import life.qbic.portal.sampletracking.app.SampleTrackingPortletController

@Log4j2
class DependencyManager {
    private PortletView portletView


    DependencyManager() {
        initializeDependencies()
    }

    private void initializeDependencies() {
        PortletController controller = new SampleTrackingPortletController()
        SampleFileReceiver sampleFileReceiver = new SampleFileReceiver()
        PortletView view = new PortletView(controller, sampleFileReceiver)


        //make view available for UI class
        this.portletView = view
    }

    protected PortletView getPortletView() {
        return this.portletView
    }
}
