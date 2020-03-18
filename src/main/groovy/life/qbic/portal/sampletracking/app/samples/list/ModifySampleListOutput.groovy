package life.qbic.portal.sampletracking.app.samples.list

import life.qbic.datamodel.samples.Sample

interface ModifySampleListOutput {
    void addSamples(Sample... samples)
    void removeSamples(Sample... samples)
    void clearSamples()
}