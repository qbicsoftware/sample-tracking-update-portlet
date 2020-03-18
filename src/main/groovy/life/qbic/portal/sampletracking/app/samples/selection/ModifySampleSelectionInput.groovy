package life.qbic.portal.sampletracking.app.samples.selection

import life.qbic.datamodel.samples.Sample

interface ModifySampleSelectionInput {
    void selectSamples(Sample... samples)
    void deselectSamples(Sample... samples)
    void clearSelection()
}