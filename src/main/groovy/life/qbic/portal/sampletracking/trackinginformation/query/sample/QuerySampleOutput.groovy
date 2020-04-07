package life.qbic.portal.sampletracking.trackinginformation.query.sample

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.UseCaseOutput

interface QuerySampleOutput extends UseCaseOutput {

    /**
     * Publishes a sample object.
     * @param sample
     * @return
     */
    def publishSample(Sample sample)
}