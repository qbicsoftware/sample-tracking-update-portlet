package life.qbic.portal.sampletracking.web.controllers

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status

interface PortletController {

    //query
    void queryAllLocationsForPerson(String email)
    //selection
    void selectSamplesById(List<String> sampleIds)

    void selectSampleById(String sampleId)

    void selectSamplesFromFile(FileOutputStream uploadedFile)

    void clearSelection()

    void updateSamples(List<String> sampleIds, Location desiredLocation, Status desiredStatus)
}