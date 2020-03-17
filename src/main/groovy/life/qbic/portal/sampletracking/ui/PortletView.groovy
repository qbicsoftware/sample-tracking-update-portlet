package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.GridLayout
import com.vaadin.ui.Upload.Receiver
import life.qbic.datamodel.samples.Location
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.app.samples.query.SampleStatusOutput
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdateOutput

class PortletView extends GridLayout implements SampleStatusOutput, SampleUpdateOutput{
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
        return null
    }

    @Override
    def updateCurrentLocation(Location location) {
        return null
    }

    @Override
    def invokeOnError(String msg) {
        // The use cases classes will call this method,
        // if something goes wrong with the sample tracking
        // So make a notification message for the user here
        return null
    }
}
