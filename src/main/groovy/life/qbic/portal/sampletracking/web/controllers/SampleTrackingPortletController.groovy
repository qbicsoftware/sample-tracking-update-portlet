package life.qbic.portal.sampletracking.web.controllers

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryInput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateInput

class SampleTrackingPortletController implements PortletController {

    private final SampleTrackingUpdateInput sampleUpdateInput

    private final SampleTrackingQueryInput sampleLocation

    private SampleTrackingPortletController() {
        throw new AssertionError()
    }

    SampleTrackingPortletController(SampleTrackingUpdateInput sampleUpdateInput, SampleTrackingQueryInput sampleLocation) {
        this.sampleUpdateInput = sampleUpdateInput
        this.sampleLocation = sampleLocation
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
// TODO implement
    }

    @Override
    void updateSamples(List<String> sampleIds, Location desiredLocation, Status desiredStatus) {
        this.sampleUpdateInput.setCurrentSampleLocation(sampleIds, desiredLocation)
        this.sampleUpdateInput.setSampleStatus(sampleIds, desiredStatus)
    }
}
