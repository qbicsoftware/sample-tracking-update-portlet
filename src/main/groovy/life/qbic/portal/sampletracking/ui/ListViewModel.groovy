package life.qbic.portal.sampletracking.ui

import life.qbic.datamodel.samples.Sample

interface ListViewModel {
    void addSamples(Sample... samples)
    void removeSamples(Sample... samples)
    void clear()
}