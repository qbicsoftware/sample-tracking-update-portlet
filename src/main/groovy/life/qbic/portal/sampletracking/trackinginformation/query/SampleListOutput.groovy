package life.qbic.portal.sampletracking.trackinginformation.query

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.UseCaseOutput

interface SampleListOutput extends UseCaseOutput {

    def addSampleToList(Sample sample)
}