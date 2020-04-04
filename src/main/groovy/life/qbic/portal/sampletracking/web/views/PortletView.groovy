package life.qbic.portal.sampletracking.web.views

import com.vaadin.server.Page
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Notification
import groovy.util.logging.Log4j2
import life.qbic.portal.sampletracking.web.StyledNotification
import life.qbic.portal.sampletracking.web.ViewModel
import life.qbic.portal.sampletracking.web.controllers.PortletController

@Log4j2
class PortletView extends HorizontalLayout {
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
        registerListeners()
    }

    private def initLayout() {
        this.setWidth("100%")
        this.addComponents(this.sampleImport, this.sampleList, this.sampleControls)
    }

    private def registerListeners() {

        this.portletViewModel.successNotifications.addPropertyChangeListener { evt ->
            if (evt instanceof ObservableList.ElementAddedEvent) {
                // show notification
                showNotification(evt.newValue.toString(), Notification.Type.HUMANIZED_MESSAGE)
                // remove displayed message
                portletViewModel.successNotifications.remove(evt.newValue)
            }
        }

        this.portletViewModel.failureNotifications.addPropertyChangeListener { evt ->
            if (evt instanceof ObservableList.ElementAddedEvent) {
                // show notification
                showNotification(evt.newValue.toString(), Notification.Type.ERROR_MESSAGE)
                // remove displayed message
                portletViewModel.failureNotifications.remove(evt.newValue)
            }
        }
    }

    private static def showNotification(String message, Notification.Type type) {
        StyledNotification notification = new StyledNotification(message, type)
        notification.show(Page.getCurrent())
    }
}
