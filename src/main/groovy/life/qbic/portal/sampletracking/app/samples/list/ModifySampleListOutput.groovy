package life.qbic.portal.sampletracking.app.samples.list

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.app.samples.UseCaseOutput

interface ModifySampleListOutput extends UseCaseOutput{
    void addSamples(Sample... samples)
    void removeSamples(Sample... samples)
    void clearSamples()
}