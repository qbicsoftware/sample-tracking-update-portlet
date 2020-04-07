package life.qbic.portal.sampletracking.web.controllers

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.trackinginformation.query.locations.QueryAvailableLocationsInput
import life.qbic.portal.sampletracking.trackinginformation.query.sample.QuerySampleInput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateInput

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Log4j2
class SampleTrackingPortletController implements PortletController {

    private final SampleTrackingUpdateInput sampleUpdateInput

    private final QueryAvailableLocationsInput queryAvailableLocationsInput
    private final QuerySampleInput querySampleInput

    private SampleTrackingPortletController() {
        throw new AssertionError()
    }

    SampleTrackingPortletController(SampleTrackingUpdateInput sampleUpdateInput, QueryAvailableLocationsInput queryAvailableLocationsInput, QuerySampleInput querySampleInput) {
        this.sampleUpdateInput = sampleUpdateInput
        this.queryAvailableLocationsInput = queryAvailableLocationsInput
        this.querySampleInput = querySampleInput
    }

    @Override
    void queryAllLocationsForPerson(String email) {
        queryAvailableLocationsInput.availableLocationsForPerson(email)
    }

    @Override
    void selectSamplesById(List<String> samples) {
        for (sampleId in samples) {
            selectSampleById(sampleId)
        }
    }

    @Override
    void selectSampleById(String sampleId) {
        this.querySampleInput.querySampleById(sampleId)
    }

    @Override
    void selectSamplesFromFile(FileOutputStream uploadedFile) {
// TODO implement
    }

    @Override
    void updateSamples(List<String> sampleIds, Location desiredLocation, Status desiredStatus, LocalDateTime date) {
        Location location = desiredLocation
        location?.arrivalDate = Date.from(date.atZone(ZoneId.systemDefault()).toInstant())
        location?.status = desiredStatus

        for (sampleId in sampleIds) {
            this.sampleUpdateInput.setCurrentSampleLocation(sampleId, location)
        }
    }
}
