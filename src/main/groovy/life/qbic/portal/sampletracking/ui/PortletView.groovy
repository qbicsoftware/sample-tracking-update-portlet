package life.qbic.portal.sampletracking.ui

import com.vaadin.server.Page
import com.vaadin.ui.GridLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Notification
import com.vaadin.ui.Upload.Receiver
import life.qbic.datamodel.samples.Location
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.app.samples.query.SampleStatusOutput
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdateOutput

class PortletView extends HorizontalLayout implements SampleStatusOutput, SampleUpdateOutput{
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

    @Override
    def updateAvailableLocations(List<Location> locations) {
        // This method will be called from the use case classes, once they have retreived
        // location information
        return null
    }

    @Override
    def updateCurrentLocation(String sampleId, Location location) {
        // This method will be called from the use case classes, once they have
        // current location information found
        return null
    }

    @Override
    def invokeOnError(String msg) {
        // The use cases classes will call this method,
        // if something goes wrong with the sample tracking
        // So make a notification message for the user here
        StyledNotification notification = new StyledNotification("Ooooops something went wrong!", msg, Notification.Type.ERROR_MESSAGE)
        notification.show(Page.getCurrent())
        return null
    }
}
