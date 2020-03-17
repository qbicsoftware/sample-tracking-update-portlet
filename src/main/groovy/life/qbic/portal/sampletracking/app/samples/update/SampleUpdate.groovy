package life.qbic.portal.sampletracking.app.samples.update

import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status

interface SampleUpdate {

    def setSampleStatus(String sampleId, Status sampleStatus, SampleUpdateOutput output)

    def setCurrentSampleLocation(String sampleId, Location location, SampleUpdateOutput output)

}