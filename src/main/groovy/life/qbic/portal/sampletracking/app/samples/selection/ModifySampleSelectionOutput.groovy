package life.qbic.portal.sampletracking.app.samples.selection

import life.qbic.datamodel.samples.Sample

interface ModifySampleSelectionOutput {
    void addSamples(Sample... samples)
    void removeSamples(Sample... samples)
    void clearSamples()
}