package life.qbic.portal.sampletracking.web.controllers

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryInput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateInput

@Log4j2
class SampleTrackingPortletController implements PortletController {

    private final SampleTrackingUpdateInput sampleUpdateInput

    private final SampleTrackingQueryInput sampleQueryInput

    private SampleTrackingPortletController() {
        throw new AssertionError()
    }

    SampleTrackingPortletController(SampleTrackingUpdateInput sampleUpdateInput, SampleTrackingQueryInput sampleLocation) {
        this.sampleUpdateInput = sampleUpdateInput
        this.sampleQueryInput = sampleLocation
    }

    @Override
    void queryAllLocationsForPerson(String email) {
        sampleQueryInput.availableLocationsForPerson(email)
    }

    @Override
    void selectSamplesById(List<String> samples) {
        for (sampleId in samples) {
            selectSampleById(sampleId)
        }
    }

    @Override
    void selectSampleById(String sampleId) {
        this.sampleQueryInput.querySampleById(sampleId)
    }

    @Override
    void selectSamplesFromFile(FileOutputStream uploadedFile) {
// TODO implement
    }

    @Override
    void updateSamples(List<String> sampleIds, Location desiredLocation, Status desiredStatus) {
        this.sampleUpdateInput.setCurrentSampleLocation(sampleIds, desiredLocation)
        this.sampleUpdateInput.setSampleStatus(sampleIds, desiredStatus)
    }
}
