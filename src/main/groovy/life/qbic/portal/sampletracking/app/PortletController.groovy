package life.qbic.portal.sampletracking.app

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status

interface PortletController {

    //query
    void queryAllLocationsForPerson(String email)
    //selection
    void selectSamples(Sample... samples)

    void selectSamples(String... sampleIds)

    void querySampleById(String sampleId)

    void clearSelection()

    void updateSamples(List<String> sampleIds, Location desiredLocation, Status desiredStatus)
}
