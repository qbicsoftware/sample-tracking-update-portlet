package life.qbic.portal.sampletracking.app

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.app.samples.list.SampleListModification
import life.qbic.portal.sampletracking.app.samples.query.SampleLocation
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdate
import life.qbic.portal.sampletracking.app.samples.update.SampleUpdateOutput
import life.qbic.portal.sampletracking.ui.PortletView

class SampleTrackingPortletController implements PortletController {

    private final SampleUpdate sampleUpdateInput

    private final SampleLocation sampleLocation

    private final SampleListModification sampleListModification

    private SampleTrackingPortletController() {
        new AssertionError()
    }

    SampleTrackingPortletController(SampleUpdate sampleUpdateInput, SampleLocation sampleLocation, SampleListModification sampleListModification) {
        this.sampleUpdateInput = sampleUpdateInput
        this.sampleLocation = sampleLocation
        this.sampleListModification = sampleListModification
    }

    @Override
    void queryAllLocationsForPerson(String email) {
        sampleLocation.availableLocationsForPerson(email)
    }

    @Override
    void selectSamples(Sample... samples) {

    }

    @Override
    void clearSelection() {
        this.sampleListModification.clearSelection()
    }
}
