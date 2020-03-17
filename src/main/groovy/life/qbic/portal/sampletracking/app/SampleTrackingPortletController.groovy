package life.qbic.portal.sampletracking.app

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.app.samples.query.SampleLocation
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdate
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdateOutput
import life.qbic.portal.sampletracking.ui.PortletView

class SampleTrackingPortletController implements PortletController {

    private final SampleUpdate sampleUpdateInput

    private final SampleLocation sampleLocation

    private final PortletView view

    SampleTrackingPortletController() {
        new AssertionError()
    }

    SampleTrackingPortletController(SampleUpdate sampleUpdateInput, SampleLocation sampleLocation, PortletView view) {
        this.sampleUpdateInput = sampleUpdateInput
        this.sampleLocation = sampleLocation
        this.view = view
    }

    void queryAllLocationsForPerson() {
        def email = "fake@gmail.com"  // TODO Implement real email determination from logged in user
        sampleLocation.availableLocationsForPerson(email, view)
    }

    @Override
    def selectSingleSample(String sampleId) {
        return null
    }
}
