package life.qbic.portal.sampletracking.web.controllers

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.trackinginformation.list.ModifySampleListInput
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryInput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateInput
import life.qbic.portal.sampletracking.web.controllers.PortletController

class SampleTrackingPortletController implements PortletController {

    private final SampleTrackingUpdateInput sampleUpdateInput

    private final SampleTrackingQueryInput sampleLocation

    private final ModifySampleListInput sampleListModification

    private SampleTrackingPortletController() {
        throw new AssertionError()
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
    void selectSamplesById(List<String> samples) {
// TODO: implement
    }

    @Override
    void selectSampleById(String sampleId) {
// TODO: implement
    }

    @Override
    void selectSamplesFromFile(FileOutputStream uploadedFile) {
// TODO implement
    }

    @Override
    void clearSelection() {
        this.sampleListModification.clearSelection()
    }

    @Override
    void updateSamples(List<String> sampleIds, Location desiredLocation, Status desiredStatus) {
        this.sampleUpdateInput.setCurrentSampleLocation(sampleIds, desiredLocation)
        this.sampleUpdateInput.setSampleStatus(sampleIds, desiredStatus)
    }
}
