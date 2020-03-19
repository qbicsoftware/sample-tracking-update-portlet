package life.qbic.portal.sampletracking.app.samples.list

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample

@Log4j2
class ModifySampleList implements ModifySampleListInput {

    ModifySampleListOutput output

    ModifySampleList() {

    }

    ModifySampleList(ModifySampleListOutput output) {
        this()
        this.injectSampleListOutput(output)
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


    /**
     * This method is an alternative to do dependency injection.
     * It is used to avoid circular dependencies.
     * The method does set the sample list output only once and does nothing if it is already set.
     */
    void injectSampleListOutput(ModifySampleListOutput sampleListOutput) {
        if (this.output) {
            log.warn("Trying to overwrite use case output. Skipping.")
        } else {
            this.output = sampleListOutput
        }
    }
}
