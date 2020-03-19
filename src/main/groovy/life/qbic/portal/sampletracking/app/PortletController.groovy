package life.qbic.portal.sampletracking.app

import life.qbic.datamodel.samples.Sample

interface PortletController {

    //query
    void queryAllLocationsForPerson(String email)
    //selection
    void selectSamples(Sample... samples)

    void selectSamples(String... sampleIds)

    void clearSelection()

}
