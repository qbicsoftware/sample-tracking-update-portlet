package life.qbic.portal.sampletracking.web.views

import com.vaadin.ui.HorizontalLayout
import life.qbic.portal.sampletracking.web.StyledNotification
import life.qbic.portal.sampletracking.web.ViewModel
import life.qbic.portal.sampletracking.web.controllers.PortletController

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

class PortletView extends HorizontalLayout {
    final private PortletController controller
    final private ViewModel portletViewModel

    private SampleList sampleList
    private ControlElements sampleControls
    private SampleImport sampleImport
    private StyledNotification styledNotification

    PortletView(PortletController portletController, ViewModel portletViewModel,
                SampleList sampleList, ControlElements sampleModifyControls, SampleImport sampleImport) {
        super()
        this.controller = portletController
        this.portletViewModel = portletViewModel
        this.sampleList = sampleList
        this.sampleControls = sampleModifyControls
        this.sampleImport = sampleImport
        this.styledNotification = styledNotification
        initLayout()
        this.showNotifications()
    }

    private def initLayout() {
        this.addComponents(this.sampleImport, this.sampleList, this.sampleControls)

    }

    private def showNotifications() {
        this.portletViewModel.notifications.addPropertyChangeListener( new PropertyChangeListener() {
            @Override
            void propertyChange(PropertyChangeEvent evt)
            {
                styledNotification(portletViewModel.notifications.forEach())
            }
        })
    }
}
