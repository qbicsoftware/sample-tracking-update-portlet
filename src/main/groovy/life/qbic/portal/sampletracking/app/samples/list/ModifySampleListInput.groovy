package life.qbic.portal.sampletracking.app.samples.list

import life.qbic.datamodel.samples.Sample

interface ModifySampleListInput {
    void selectSamples(Sample... samples)

    void deselectSamples(Sample... samples)

    void clearSelection()

    void injectSampleListOutput(ModifySampleListOutput sampleListOutput)
}