package life.qbic.portal.sampletracking.trackinginformation.list

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.UseCaseOutput

interface ModifySampleListOutput extends UseCaseOutput {

    void addSamples(List<Sample> samples)

    void removeSamples(List<Sample> samples)

    void clearSamples()
}