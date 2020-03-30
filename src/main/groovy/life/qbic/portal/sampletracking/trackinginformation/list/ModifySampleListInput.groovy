package life.qbic.portal.sampletracking.trackinginformation.list

import life.qbic.datamodel.samples.Sample

interface ModifySampleListInput {
    void selectSamples(List<Sample> samples)

    void deselectSamples(List<Sample> samples)

    void clearSelection()

    void injectSampleListOutput(ModifySampleListOutput sampleListOutput)
}