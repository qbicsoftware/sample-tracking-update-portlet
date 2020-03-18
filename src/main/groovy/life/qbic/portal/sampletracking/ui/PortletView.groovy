package life.qbic.portal.sampletracking.ui

import com.vaadin.server.Page
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Notification
import com.vaadin.ui.Upload.Receiver
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.app.samples.query.SampleStatusOutput
import life.qbic.portal.sampletracking.app.samples.list.ModifySampleListOutput
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdateOutput

class PortletView extends HorizontalLayout implements SampleStatusOutput, SampleUpdateOutput, ModifySampleListOutput{
    private PortletController controller
    private Receiver receiver
    private List<Sample> samples

    private SampleList sampleList
    private SampleModifyControls sampleControls
    private SampleImport sampleImport

    PortletView(PortletController portletController, Receiver receiver) {
        super()
        this.samples = new ArrayList<>()
        this.controller = portletController
        this.receiver = receiver
        initLayout()
    }

    private def initLayout() {
        //TODO remove
        Sample testSample = new Sample()
        Location testLocation = new Location()
        testLocation.setName("Nowhere")
        testLocation.setStatus(Status.PROCESSING)
        testSample.setCode("ABCDE")
        testSample.setCurrentLocation(testLocation)
        this.samples.add(testSample)

        this.sampleList = new SampleList(this.samples)
        this.sampleControls= new SampleModifyControls(this.controller)
        this.sampleImport = new SampleImport(this.controller, this.receiver)
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

    @Override
    void addSamples(Sample... samples) {

    }

    @Override
    void removeSamples(Sample... samples) {

    }

    @Override
    void clearSamples() {
        this.samples.clear()
        this.sampleList.refreshView()
    }
}
