package life.qbic.portal.sampletracking.app

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.app.samples.list.ModifySampleListInput
import life.qbic.portal.sampletracking.app.samples.query.SampleTrackingQueryInput
import life.qbic.portal.sampletracking.app.samples.update.SampleTrackingUpdateInput

class SampleTrackingPortletController implements PortletController {

    private final SampleTrackingUpdateInput sampleUpdateInput

    private final SampleTrackingQueryInput sampleLocation

    private final ModifySampleListInput sampleListModification

    private SampleTrackingPortletController() {
        new AssertionError()
    }

    SampleTrackingPortletController(SampleTrackingUpdateInput sampleUpdateInput, SampleTrackingQueryInput sampleLocation, ModifySampleListInput sampleListModification) {
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
    void selectSamples(String... sampleIds) {

    }

    @Override
    void clearSelection() {
        this.sampleListModification.clearSelection()
    }

    @Override
    void updateSamples(String sampleIds, Location desiredLocation, Status desiredStatus) {
        this.sampleUpdateInput.setCurrentSampleLocation(sampleIds, desiredLocation)
        this.sampleUpdateInput.setSampleStatus(sampleIds, desiredStatus)
    }
}