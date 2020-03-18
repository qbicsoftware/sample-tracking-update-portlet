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

    SampleTrackingPortletController() {
        new AssertionError()
    }

    SampleTrackingPortletController(SampleUpdate sampleUpdateInput, SampleLocation sampleLocation) {
        this.sampleUpdateInput = sampleUpdateInput
        this.sampleLocation = sampleLocation
    }

    @Override
    void queryAllLocationsForPerson(String email) {
        email = "fake@gmail.com"  // TODO Implement real email determination from logged in user
        sampleLocation.availableLocationsForPerson(email)
    }

    @Override
    void selectSamples(Sample... samples) {

    }

    @Override
    void clearSelection() {

    }
}
