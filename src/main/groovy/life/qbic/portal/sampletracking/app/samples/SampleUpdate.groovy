package life.qbic.portal.sampletracking.app.samples

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample

/**
 *
 */
interface SampleUpdate {

    // TODO should throw a SampleUpdateException
    // We do not need an output interface, as the method will
    // throw an exception if something goes wrong
    def execute()

}