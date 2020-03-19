package life.qbic.portal.sampletracking

import com.vaadin.annotations.Widgetset
import com.vaadin.server.Page
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Layout
import com.vaadin.ui.VerticalLayout
import groovy.util.logging.Log4j2
import life.qbic.portal.portlet.QBiCPortletUI
import life.qbic.portal.sampletracking.ui.StyledNotification

@Widgetset("life.qbic.portal.sampletracking.AppWidgetSet")
@Log4j2
class SampleUpdatePortlet extends QBiCPortletUI {
    private DependencyManager dependencyManager

    SampleUpdatePortlet() {
        super()
        init()
    }

    private def init() {
        try {
            this.dependencyManager = new DependencyManager()
        } catch (Exception e) {
            log.error("Could not initialize {}", SampleUpdatePortlet.getCanonicalName(), e)
        }
    }

    @Override
    protected Layout getPortletContent(VaadinRequest vaadinRequest) {
        def layout
        log.info "Generating content for class {}", SampleUpdatePortlet.getCanonicalName()
        try {
            layout = this.dependencyManager.getPortletView()
        } catch (Exception e) {
            log.error("Failed generatind content for class {}", SampleUpdatePortlet.getCanonicalName())
            log.error(e)
            String eCaption = "Portlet not available"
            String eMessage = "This portlet is currently not available. Please contact the site administrator."
            StyledNotification initializationErrorNotification = new StyledNotification(eCaption, eMessage)
            initializationErrorNotification.show(Page.getCurrent())
            layout = new VerticalLayout()
        }
        log.info "Finished content generation for class {}", SampleUpdatePortlet.getCanonicalName()
        return layout
    }
}
