package life.qbic.portal.sampletracking

import groovy.util.logging.Log4j2
import life.qbic.portal.sampletracking.ui.PortletController
import life.qbic.portal.sampletracking.ui.PortletView

@Log4j2
class DependencyManager {
    private PortletView portletView


    DependencyManager() {
        initializeDependencies()
    }

    private void initializeDependencies() {
        PortletController controller = new PortletController()
        PortletView view = new PortletView(controller)


        //make view available for UI class
        this.portletView = view
    }

    protected PortletView getPortletView() {
        return this.portletView
    }
}
