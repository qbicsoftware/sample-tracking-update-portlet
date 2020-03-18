package life.qbic.portal.sampletracking.app.samples.selection

import life.qbic.datamodel.samples.Sample

class ModifySampleSelection implements ModifySampleSelectionInput{

    ModifySampleSelectionOutput output

    private ModifySampleSelection() {
        // this cannot be reached from the outside
    }

    ModifySampleSelection(ModifySampleSelectionOutput output) {
        this.output = output
    }

    @Override
    void selectSamples(Sample... samples) {
        this.output.addSamples(samples)
    }

    @Override
    void deselectSamples(Sample... samples) {
        this.output.removeSamples(samples)
    }

    @Override
    void clearSelection() {
        this.output.clearSamples()
    }
}
